package com.egoriku.grodnoroads.screen.map

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.domain.model.*
import com.egoriku.grodnoroads.screen.map.store.CamerasStore
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Intent.ReportAction
import com.egoriku.grodnoroads.screen.map.store.LocationStore
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Intent.*
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MapComponentImpl(
    componentContext: ComponentContext
) : MapComponent, ComponentContext by componentContext, KoinComponent {

    private val stationaryStore = instanceKeeper.getStore { get<CamerasStore>() }
    private val locationStore = instanceKeeper.getStore { get<LocationStore>() }

    override val usersActions: Flow<List<MapEvent>>
        get() = stationaryStore.states.map { it.userActions }

    override val appMode: Flow<AppMode>
        get() = locationStore.states.map { it.appMode }

    override val location: Flow<UserPosition>
        get() = locationStore.states.map { it.userPosition }

    override val stationary: Flow<List<Camera>>
        get() = stationaryStore.states.map { it.stationaryCameras }

    override fun reportAction(latLng: LatLng, type: UserActionType) {
        stationaryStore.accept(
            ReportAction(latLng = latLng, type = type)
        )
    }

    override val labels: Flow<Label> = locationStore.labels

    override fun startLocationUpdates() = locationStore.accept(StartLocationUpdates)

    override fun stopLocationUpdates() = locationStore.accept(StopLocationUpdates)

    override fun onLocationDisabled() = locationStore.accept(DisabledLocation)
}