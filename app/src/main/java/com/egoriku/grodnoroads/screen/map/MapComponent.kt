package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.domain.model.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val appMode: Flow<AppMode>

    val location: Flow<UserPosition>

    val stationary: Flow<List<Camera>>
    val usersActions: Flow<List<MapEvent>>

    fun reportAction(latLng: LatLng, type: UserActionType)

    fun startLocationUpdates()
    fun stopLocationUpdates()
}