package com.egoriku.grodnoroads.guidance.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.coroutines.coroutineScope
import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.coroutines.flow.toCFlow
import com.egoriku.grodnoroads.eventreporting.domain.component.EventReportingComponent
import com.egoriku.grodnoroads.eventreporting.domain.component.buildEventReportingComponent
import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation
import com.egoriku.grodnoroads.guidance.domain.model.MapBottomSheet
import com.egoriku.grodnoroads.guidance.domain.model.MapConfig
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent
import com.egoriku.grodnoroads.guidance.domain.model.MapEvents
import com.egoriku.grodnoroads.guidance.domain.model.event.AlertEvent
import com.egoriku.grodnoroads.guidance.domain.model.event.Notification
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.Intent.CheckLocation
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.Intent.ChooseLocation
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.Intent.StartDriveMode
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.Intent.StopDriveMode
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStore
import com.egoriku.grodnoroads.guidance.domain.store.location.LocationStore.Label
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore
import com.egoriku.grodnoroads.guidance.domain.util.alertMessagesTransformation
import com.egoriku.grodnoroads.guidance.domain.util.alertSoundTransformation
import com.egoriku.grodnoroads.guidance.domain.util.filterMapEvents
import com.egoriku.grodnoroads.guidance.domain.util.overSpeedTransformation
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.quicksettings.domain.component.QuickSettingsComponent
import com.egoriku.grodnoroads.quicksettings.domain.component.buildQuickSettingsComponent
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import com.egoriku.grodnoroads.specialevent.domain.component.specialevent.SpecialEventComponent
import com.egoriku.grodnoroads.specialevent.domain.component.specialevent.buildSpecialEventComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildGuidanceComponent(
    componentContext: ComponentContext,
    onOpenReporting: () -> Unit
): GuidanceComponent =
    GuidanceComponentImpl(componentContext, onOpenReporting)

@OptIn(FlowPreview::class)
internal class GuidanceComponentImpl(
    componentContext: ComponentContext,
    private val onOpenReporting: () -> Unit
) : GuidanceComponent, ComponentContext by componentContext, KoinComponent {

    private val eventReportingComponent: EventReportingComponent =
        buildEventReportingComponent(childContext("event_reporting"))

    override val quickSettingsComponent: QuickSettingsComponent =
        buildQuickSettingsComponent(childContext("quick_settings"))

    override val specialEventComponent: SpecialEventComponent =
        buildSpecialEventComponent(childContext(key = "special_event"))

    private val dialogStore: DialogStore = instanceKeeper.getStore(::get)
    private val locationStore: LocationStore = instanceKeeper.getStore(::get)
    private val mapConfigStore: MapConfigStore = instanceKeeper.getStore(::get)
    private val mapEventsStore: MapEventsStore = instanceKeeper.getStore(::get)

    private val stationary = mapEventsStore.states.map { it.stationaryCameras }
    private val mediumSpeed = mapEventsStore.states.map { it.mediumSpeedCameras }
    private val mobile = mapEventsStore.states.map { it.mobileCameras }
    private val reports = mapEventsStore.states.map { it.reports }

    private val mapInfo = mapConfigStore.states.map { it.mapInternalConfig.mapInfo }
    private val alertInfo = mapConfigStore.states.map { it.mapInternalConfig.alertsInfo }

    private val coroutineScope = coroutineScope(Dispatchers.Main)

    private val backCallback = BackCallback {
        when (mapConfigStore.state.currentAppMode) {
            AppMode.Drive -> stopDriveMode()
            AppMode.ChooseLocation -> cancelChooseLocationFlow()
            else -> {}
        }
    }

    init {
        backHandler.register(backCallback)

        mapConfigStore.states.map { it.currentAppMode }
            .onEach { backCallback.isEnabled = it != AppMode.Default }
            .launchIn(coroutineScope)

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
                            alertEvents.tryEmit(
                                AlertEvent.CameraLimit(
                                    id = alert.id,
                                    speedLimit = alert.speedLimit,
                                    cameraType = alert.cameraType
                                )
                            )
                        }
                        is Alert.IncidentAlert -> {
                            alertEvents.tryEmit(
                                AlertEvent.IncidentAlert(
                                    id = alert.id,
                                    mapEventType = alert.mapEventType
                                )
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
                    alertEvents.tryEmit(AlertEvent.OverSpeed)
                }
            }
            .launchIn(coroutineScope)

        alertInfo
            .distinctUntilChanged()
            .onEach {
                alertEvents.tryEmit(
                    AlertEvent.VolumeChange(
                        volume = it.alertsVolumeLevel.volumeLevel,
                        loudness = it.alertsVolumeLevel.loudness.value
                    )
                )
            }
            .launchIn(coroutineScope)
    }

    override val notificationEvents: MutableSharedFlow<Notification> =
        MutableSharedFlow(extraBufferCapacity = 1)

    override val alertEvents: MutableSharedFlow<AlertEvent> =
        MutableSharedFlow(extraBufferCapacity = 1)

    override val appMode: CFlow<AppMode>
        get() = mapConfigStore.states.map { it.currentAppMode }.toCFlow()

    override val mapBottomSheet: CFlow<MapBottomSheet>
        get() = dialogStore.states.map { it.mapBottomSheet }.toCFlow()

    override val userCount: CFlow<Int>
        get() = mapEventsStore.states.map { it.userCount }.toCFlow()

    override val mapConfig: CFlow<MapConfig>
        get() = mapConfigStore.states.map {
            MapConfig(
                zoomLevel = it.zoomLevel,
                googleMapStyle = it.mapInternalConfig.googleMapStyle,
                trafficJanOnMap = it.mapInternalConfig.trafficJanOnMap,
                keepScreenOn = it.mapInternalConfig.keepScreenOn,
                alertRadius = it.alertRadius,
                alertsEnabled = it.mapInternalConfig.alertsInfo.alertsEnabled,
                isChooseInDriveMode = it.isChooseInDriveMode
            )
        }.toCFlow()

    override val mapEvents: CFlow<MapEvents>
        get() = combine(
            flow = reports,
            flow2 = stationary,
            flow3 = mobile,
            flow4 = mediumSpeed,
            flow5 = mapInfo,
            transform = filterMapEvents()
        ).flowOn(Dispatchers.Default)
            .toCFlow()

    override val lastLocation: CFlow<LastLocation>
        get() = locationStore.states.map { it.lastLocation }.toCFlow()

    override val initialLocation: CFlow<LatLng>
        get() = locationStore.states.map { it.initialLocation }.toCFlow()

    override val alerts: CFlow<ImmutableList<Alert>>
        get() = combine(
            flow = mapEvents,
            flow2 = lastLocation,
            flow3 = mapConfig,
            flow4 = appMode,
            transform = alertMessagesTransformation()
        ).flowOn(Dispatchers.Default)
            .toCFlow()

    override val speedLimit: CFlow<Int>
        get() = combine(
            flow = alerts,
            flow2 = lastLocation,
            transform = overSpeedTransformation()
        ).flowOn(Dispatchers.Default)
            .toCFlow()

    override fun startDriveMode() {
        locationStore.accept(LocationStore.Intent.StartLocationUpdates)
        mapConfigStore.accept(StartDriveMode)
    }

    override fun stopDriveMode() {
        locationStore.accept(LocationStore.Intent.StopLocationUpdates)
        mapConfigStore.accept(StopDriveMode)
    }

    override fun requestCurrentLocation() {
        locationStore.accept(LocationStore.Intent.RequestCurrentLocation)
    }

    override fun setLocation(latLng: LatLng) {
        locationStore.accept(LocationStore.Intent.SetUserLocation(latLng))
    }

    override fun processReporting(params: ReportParams) {
        eventReportingComponent.report(
            params = params,
            latLng = locationStore.state.userLocation.latLng
        )

        if (!mapConfigStore.state.isChooseInDriveMode) {
            locationStore.accept(LocationStore.Intent.InvalidateLocation)
        }
        mapConfigStore.accept(ChooseLocation.CancelChooseLocation)

        notificationEvents.tryEmit(Notification.RepostingSuccess)
    }

    override fun switchToChooseLocationFlow() {
        mapConfigStore.accept(ChooseLocation.OpenChooseLocation)
    }

    override fun cancelChooseLocationFlow() {
        if (!mapConfigStore.state.isChooseInDriveMode) {
            locationStore.accept(LocationStore.Intent.InvalidateLocation)
        }
        mapConfigStore.accept(ChooseLocation.CancelChooseLocation)
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

    override fun openQuickSettings() {
        dialogStore.accept(DialogStore.Intent.OpenQuickSettings)
    }

    override fun closeDialog() = dialogStore.accept(DialogStore.Intent.CloseDialog)

    private fun bindLocationLabel(label: Label) = when (label) {
        is Label.NewLocation -> mapConfigStore.accept(CheckLocation(label.latLng))
    }
}