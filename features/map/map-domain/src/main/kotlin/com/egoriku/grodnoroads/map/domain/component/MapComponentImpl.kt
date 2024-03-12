package com.egoriku.grodnoroads.map.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.eventreporting.domain.component.EventReportingComponent
import com.egoriku.grodnoroads.eventreporting.domain.component.buildEventReportingComponent
import com.egoriku.grodnoroads.eventreporting.domain.model.ReportingResult
import com.egoriku.grodnoroads.map.domain.extension.coroutineScope
import com.egoriku.grodnoroads.map.domain.model.*
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.Intent.*
import com.egoriku.grodnoroads.map.domain.store.dialog.DialogStore
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.Label
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore
import com.egoriku.grodnoroads.map.domain.store.quickactions.QuickActionsStore
import com.egoriku.grodnoroads.map.domain.store.quickactions.QuickActionsStore.QuickActionsIntent
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.map.domain.util.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

fun buildMapComponent(
    componentContext: ComponentContext,
    onOpenReporting: () -> Unit
): MapComponent = MapComponentImpl(componentContext, onOpenReporting)

@OptIn(FlowPreview::class)
internal class MapComponentImpl(
    componentContext: ComponentContext,
    private val onOpenReporting: () -> Unit
) : MapComponent, ComponentContext by componentContext, KoinComponent {

    private val eventReportingComponent: EventReportingComponent =
        buildEventReportingComponent(childContext("event_reporting"))

    private val dialogStore: DialogStore = instanceKeeper.getStore(::get)
    private val locationStore: LocationStore = instanceKeeper.getStore(::get)
    private val mapConfigStore: MapConfigStore = instanceKeeper.getStore(::get)
    private val mapEventsStore: MapEventsStore = instanceKeeper.getStore(::get)
    private val quickActionsStore: QuickActionsStore = instanceKeeper.getStore(::get)

    private val stationary = mapEventsStore.states.map { it.stationaryCameras }
    private val mediumSpeed = mapEventsStore.states.map { it.mediumSpeedCameras }
    private val mobile = mapEventsStore.states.map { it.mobileCameras }
    private val reports = mapEventsStore.states.map { it.reports }

    private val mapInfo = mapConfigStore.states.map { it.mapInternalConfig.mapInfo }
    private val alertInfo = mapConfigStore.states.map { it.mapInternalConfig.alertsInfo }

    private val coroutineScope = coroutineScope(Dispatchers.Main)

    private val soundUtil by inject<SoundUtil>()

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            locationStore.labels bindTo ::bindLocationLabel
        }

        combine(
            flow = alerts,
            flow2 = alertInfo,
            flow3 = appMode,
            transform = alertSoundTransformation()
        ).distinctUntilChanged()
            .debounce(500)
            .onEach { data ->
                data.onEach { alert ->
                    when (alert) {
                        is Alert.CameraAlert -> {
                            soundUtil.playCameraLimit(
                                id = alert.id,
                                speedLimit = alert.speedLimit,
                                cameraType = alert.cameraType
                            )
                        }

                        is Alert.IncidentAlert -> {
                            soundUtil.playIncident(
                                id = alert.id,
                                mapEventType = alert.mapEventType
                            )
                        }
                    }
                }
            }
            .launchIn(coroutineScope)

        speedLimit
            .distinctUntilChanged()
            .debounce(500)
            .onEach {
                if (it != -1) {
                    soundUtil.playOverSpeed()
                }
            }
            .launchIn(coroutineScope)

        alertInfo
            .distinctUntilChanged()
            .onEach {
                soundUtil.setVolume(level = it.alertsVolumeLevel.volumeLevel)
                soundUtil.setLoudness(loudness = it.alertsVolumeLevel.loudness.value)
            }
            .launchIn(coroutineScope)
    }

    override val appMode: Flow<AppMode>
        get() = mapConfigStore.states.map { it.appMode }

    override val mapAlertDialog: Flow<MapAlertDialog>
        get() = dialogStore.states.map { it.mapAlertDialog }

    override val userCount: Flow<Int>
        get() = mapEventsStore.states.map { it.userCount }

    override val quickActionsState: Flow<QuickActionsState>
        get() = quickActionsStore.states

    override val mapConfig: Flow<MapConfig>
        get() = mapConfigStore.states.map {
            MapConfig(
                zoomLevel = it.zoomLevel,
                googleMapStyle = it.mapInternalConfig.googleMapStyle,
                trafficJanOnMap = it.mapInternalConfig.trafficJanOnMap,
                keepScreenOn = it.mapInternalConfig.keepScreenOn,
                alertRadius = it.alertRadius,
                alertsEnabled = it.mapInternalConfig.alertsInfo.alertsEnabled
            )
        }

    override val mapEvents: Flow<ImmutableList<MapEvent>>
        get() = combine(
            flow = reports,
            flow2 = stationary,
            flow3 = mobile,
            flow4 = mediumSpeed,
            flow5 = mapInfo,
            transform = filterMapEvents()
        ).flowOn(Dispatchers.Default)

    override val lastLocation: Flow<LastLocation>
        get() = locationStore.states.map { it.lastLocation }

    override val initialLocation: Flow<LatLng>
        get() = locationStore.states.map { it.initialLocation }

    override val alerts: Flow<ImmutableList<Alert>>
        get() = combine(
            flow = mapEvents,
            flow2 = lastLocation,
            flow3 = mapConfig,
            flow4 = appMode,
            transform = alertMessagesTransformation()
        ).flowOn(Dispatchers.Default)

    override val speedLimit: Flow<Int>
        get() = combine(
            flow = alerts,
            flow2 = lastLocation,
            transform = overSpeedTransformation()
        ).flowOn(Dispatchers.Default)

    override fun startLocationUpdates() {
        locationStore.accept(LocationStore.Intent.StartLocationUpdates)
        mapConfigStore.accept(StartDriveMode)
    }

    override fun stopLocationUpdates() {
        locationStore.accept(LocationStore.Intent.StopLocationUpdates)
        mapConfigStore.accept(StopDriveMode)
    }

    override fun requestCurrentLocation() {
        locationStore.accept(LocationStore.Intent.RequestCurrentLocation)
    }

    override fun setLocation(latLng: LatLng) {
        locationStore.accept(LocationStore.Intent.SetUserLocation(latLng))
    }

    override fun processReporting(result: ReportingResult) {
        eventReportingComponent.report(result.copy(latLng = locationStore.state.userLocation.latLng))

        locationStore.accept(LocationStore.Intent.InvalidateLocation)
        mapConfigStore.accept(ChooseLocation.CancelChooseLocation)
    }

    override fun switchToChooseLocationFlow() {
        mapConfigStore.accept(ChooseLocation.OpenChooseLocation)
    }

    override fun cancelChooseLocationFlow() {
        mapConfigStore.accept(ChooseLocation.CancelChooseLocation)
        locationStore.accept(LocationStore.Intent.InvalidateLocation)
    }

    override fun startReporting(latLng: LatLng) {
        onOpenReporting()
        setLocation(latLng)
    }

    override fun setUserMapZoom(zoom: Float) {
        mapConfigStore.accept(ChooseLocation.UserMapZoom(zoom))
    }

    override fun showMarkerInfoDialog(reports: MapEvent.Reports) =
        dialogStore.accept(DialogStore.Intent.OpenMarkerInfoDialog(reports = reports))

    override fun closeDialog() = dialogStore.accept(DialogStore.Intent.CloseDialog)

    private fun bindLocationLabel(label: Label) = when (label) {
        is Label.NewLocation -> mapConfigStore.accept(CheckLocation(label.latLng))
        else -> Unit
    }

    override fun updatePreferences(pref: QuickActionsPref) {
        quickActionsStore.accept(QuickActionsIntent.Update(pref))
    }
}