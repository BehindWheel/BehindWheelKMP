package com.egoriku.grodnoroads.screen.map

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.map.domain.*
import com.egoriku.grodnoroads.screen.map.store.LocationStore
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Intent.*
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.egoriku.grodnoroads.screen.map.store.MapEventsStore
import com.egoriku.grodnoroads.screen.map.store.MapEventsStoreFactory.Intent
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

    private val locationStore = instanceKeeper.getStore { get<LocationStore>() }
    private val mapEventsStore = instanceKeeper.getStore { get<MapEventsStore>() }
    private val settingsStore = instanceKeeper.getStore { get<SettingsStore>() }

    private val mobile = mapEventsStore.states.map { it.mobileCamera }
    private val stationary = mapEventsStore.states.map { it.stationaryCameras }
    private val reports = mapEventsStore.states.map { it.reports }
    private val settings = settingsStore.states.map { it.settingsState }

    override val alertDialogState: Flow<AlertDialogState>
        get() = mapEventsStore.states.map { it.alertDialogState }

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

    override val mapSettings: Flow<MapSettings>
        get() = settingsStore.states.map {
            MapSettings(
                isTrafficEnabled = it.settingsState.trafficJam.isShow
            )
        }

    override val location: Flow<LocationState>
        get() = locationStore.states.map { it.locationState }

    override fun reportAction(latLng: LatLng, type: MapEventType) {
        mapEventsStore.accept(ReportAction(latLng = latLng, mapEventType = type))
    }

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

    override fun showMarkerInfoDialog(reports: MapEvent.Reports) =
        mapEventsStore.accept(
            Intent.OpenMarkerInfoDialog(reports = reports)
        )

    override fun closeDialog() = mapEventsStore.accept(Intent.CloseDialog)
}