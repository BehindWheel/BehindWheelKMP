package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.domain.model.*
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val appMode: Flow<AppMode>

    val location: Flow<UserPosition>

    val stationary: Flow<List<Camera>>
    val usersActions: Flow<List<MapEvent>>

    val labels: Flow<Label>

    fun reportAction(latLng: LatLng, type: UserActionType)

    fun startLocationUpdates()
    fun stopLocationUpdates()

    fun onLocationDisabled()
}