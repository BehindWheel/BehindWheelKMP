package com.egoriku.grodnoroads.screen.map

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.domain.model.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.MapEvent
import com.egoriku.grodnoroads.screen.map.domain.Alert
import com.egoriku.grodnoroads.screen.map.store.CamerasStore
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Intent.ReportAction
import com.egoriku.grodnoroads.screen.map.store.LocationStore
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Intent.*
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
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

    private val stationaryStore = instanceKeeper.getStore { get<CamerasStore>() }
    private val locationStore = instanceKeeper.getStore { get<LocationStore>() }

    private val mobile = stationaryStore.states.map { it.mobileCamera }
    private val stationary = stationaryStore.states.map { it.stationaryCameras }
    private val reports = stationaryStore.states.map { it.reports }

    override val appMode: Flow<AppMode>
        get() = locationStore.states.map { it.appMode }

    override val mapEvents: Flow<List<MapEvent>>
        get() = combine(reports, stationary, mobile) { a, b, c -> a + b + c }

    override val location: Flow<LocationState>
        get() = locationStore.states.map { it.locationState }

    override fun reportAction(latLng: LatLng, type: MapEventType) {
        stationaryStore.accept(ReportAction(latLng = latLng, mapEventType = type))
    }

    override val alerts: Flow<List<Alert>>
        get() = mapEvents
            .combine(flow = location, transform = alertMessagesTransformation())
            .flowOn(Dispatchers.Default)

    override val labels: Flow<Label> = locationStore.labels

    override fun startLocationUpdates() = locationStore.accept(StartLocationUpdates)

    override fun stopLocationUpdates() = locationStore.accept(StopLocationUpdates)

    override fun onLocationDisabled() = locationStore.accept(DisabledLocation)
}