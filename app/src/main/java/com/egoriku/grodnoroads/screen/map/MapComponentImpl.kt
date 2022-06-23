package com.egoriku.grodnoroads.screen.map

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.map.MapComponent.ReportDialogFlow
import com.egoriku.grodnoroads.screen.map.domain.*
import com.egoriku.grodnoroads.screen.map.store.DialogStore
import com.egoriku.grodnoroads.screen.map.store.DialogStoreFactory.Intent.*
import com.egoriku.grodnoroads.screen.map.store.LocationStore
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Intent.*
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.egoriku.grodnoroads.screen.map.store.MapEventsStore
import com.egoriku.grodnoroads.screen.map.store.MapEventsStoreFactory.Intent.ReportAction
import com.egoriku.grodnoroads.screen.settings.store.SettingsStore
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MapComponentImpl(
    componentContext: ComponentContext
) : MapComponent, ComponentContext by componentContext, KoinComponent {

    private val dialogStore = instanceKeeper.getStore { get<DialogStore>() }
    private val locationStore = instanceKeeper.getStore { get<LocationStore>() }
    private val mapEventsStore = instanceKeeper.getStore { get<MapEventsStore>() }
    private val settingsStore = instanceKeeper.getStore { get<SettingsStore>() }

    private val mobile = mapEventsStore.states.map { it.mobileCamera }
    private val stationary = mapEventsStore.states.map { it.stationaryCameras }
    private val reports = mapEventsStore.states.map { it.reports }
    private val settings = settingsStore.states.map { it.settingsState }

    override val mapAlertDialog: Flow<MapAlertDialog>
        get() = dialogStore.states.map { it.mapAlertDialog }

    override val appMode: Flow<AppMode>
        get() = locationStore.states.map { it.appMode }

    override val mapEvents: Flow<List<MapEvent>>
        get() = combine(
            flow = reports,
            flow2 = stationary,
            flow3 = mobile,
            flow4 = settings,
            transform = filterMapEvents()
        )

    override val mapPreferences: Flow<GrodnoRoadsMapPreferences>
        get() = settingsStore.states.map {
            GrodnoRoadsMapPreferences(
                isTrafficEnabled = it.settingsState.mapAppearance.trafficJam.isShow
            )
        }

    override val location: Flow<LocationState>
        get() = locationStore.states.map { it.locationState }

    override val alerts: Flow<List<Alert>>
        get() = combine(
            flow = mapEvents,
            flow2 = location,
            flow3 = settings,
            transform = alertMessagesTransformation()
        ).flowOn(Dispatchers.Default)

    override val labels: Flow<Label> = locationStore.labels

    override fun startLocationUpdates() = locationStore.accept(StartLocationUpdates)

    override fun stopLocationUpdates() = locationStore.accept(StopLocationUpdates)

    override fun onLocationDisabled() = locationStore.accept(DisabledLocation)

    override fun reportAction(
        latLng: LatLng,
        type: MapEventType,
        shortMessage: String,
        message: String
    ) {
        mapEventsStore.accept(
            ReportAction(
                latLng = latLng,
                mapEventType = type,
                shortMessage = shortMessage,
                message = message
            )
        )
        dialogStore.accept(CloseDialog)
    }

    override fun showMarkerInfoDialog(reports: MapEvent.Reports) =
        dialogStore.accept(OpenMarkerInfoDialog(reports = reports))

    override fun openReportFlow(reportDialogFlow: ReportDialogFlow) {
        when (reportDialogFlow) {
            is ReportDialogFlow.TrafficPolice -> dialogStore.accept(
                intent = OpenReportTrafficPoliceDialog(reportDialogFlow.latLng)
            )
            is ReportDialogFlow.RoadIncident -> dialogStore.accept(
                intent = OpenRoadIncidentDialog(reportDialogFlow.latLng)
            )
        }
    }

    override fun closeDialog() = dialogStore.accept(CloseDialog)
}