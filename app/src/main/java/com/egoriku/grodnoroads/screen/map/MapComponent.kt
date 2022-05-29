package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.domain.model.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.Alert
import com.egoriku.grodnoroads.screen.map.domain.MapEvent
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val appMode: Flow<AppMode>
    val location: Flow<LocationState>
    val mapEvents: Flow<List<MapEvent>>

    val labels: Flow<Label>

    val alerts: Flow<List<Alert>>

    fun reportAction(latLng: LatLng, type: MapEventType)

    fun startLocationUpdates()
    fun stopLocationUpdates()

    fun onLocationDisabled()
}