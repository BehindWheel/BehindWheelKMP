package com.egoriku.grodnoroads.screen.map.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.MapAlertDialog
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.screen.map.domain.component.util.alertMessagesTransformation
import com.egoriku.grodnoroads.screen.map.domain.component.MapComponent.ReportDialogFlow
import com.egoriku.grodnoroads.screen.map.domain.component.util.filterMapEvents
import com.egoriku.grodnoroads.screen.map.domain.model.*
import com.egoriku.grodnoroads.screen.map.domain.store.DialogStore
import com.egoriku.grodnoroads.screen.map.domain.store.DialogStoreFactory.Intent.*
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStore
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.Intent.*
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.Label
import com.egoriku.grodnoroads.screen.map.domain.store.MapConfigStore
import com.egoriku.grodnoroads.screen.map.domain.store.MapConfigStore.Intent
import com.egoriku.grodnoroads.screen.map.domain.store.MapEventsStore
import com.egoriku.grodnoroads.screen.map.domain.store.MapEventsStoreFactory.Intent.ReportAction
import com.egoriku.grodnoroads.screen.settings.alerts.domain.store.AlertsStore
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore
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

    private val alertsStore: AlertsStore = instanceKeeper.getStore(::get)
    private val dialogStore: DialogStore = instanceKeeper.getStore(::get)
    private val locationStore: LocationStore = instanceKeeper.getStore(::get)
    private val mapConfigStore: MapConfigStore = instanceKeeper.getStore(::get)
    private val mapEventsStore: MapEventsStore = instanceKeeper.getStore(::get)
    private val mapSettingsStore: MapSettingsStore = instanceKeeper.getStore(::get)

    private val mobile = mapEventsStore.states.map { it.mobileCamera }
    private val stationary = mapEventsStore.states.map { it.stationaryCameras }
    private val reports = mapEventsStore.states.map { it.reports }

    private val alertDistance = alertsStore.states.map { it.alertDistance }
    private val mapSettings = mapSettingsStore.states.map { it.mapSettings }

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            locationStore.labels bindTo ::bindLocationLabel
        }
    }

    override val appMode: Flow<AppMode>
        get() = locationStore.states.map { it.appMode }

    override val mapAlertDialog: Flow<MapAlertDialog>
        get() = dialogStore.states.map { it.mapAlertDialog }

    override val mapConfig: Flow<MapConfig>
        get() = mapConfigStore.states.map { it.mapConfig }

    override val mapEvents: Flow<List<MapEvent>>
        get() = combine(
            flow = reports,
            flow2 = stationary,
            flow3 = mobile,
            flow4 = mapSettings,
            transform = filterMapEvents()
        )

    override val location: Flow<LocationState>
        get() = locationStore.states.map { it.locationState }

    override val alerts: Flow<List<Alert>>
        get() = combine(
            flow = mapEvents,
            flow2 = location,
            flow3 = alertDistance,
            transform = alertMessagesTransformation()
        ).flowOn(Dispatchers.Default)

    override val labels: Flow<Label> = locationStore.labels

    override fun startLocationUpdates() = locationStore.accept(StartLocationUpdates)

    override fun stopLocationUpdates() = locationStore.accept(StopLocationUpdates)

    override fun onLocationDisabled() = locationStore.accept(DisabledLocation)

    override fun reportAction(params: ReportAction.Params) {
        mapEventsStore.accept(ReportAction(params = params))
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

    private fun bindLocationLabel(label: Label) = when (label) {
        is Label.NewLocation -> mapConfigStore.accept(Intent.CheckLocation(label.latLng))
        else -> Unit
    }
}