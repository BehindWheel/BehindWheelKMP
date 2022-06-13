package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.screen.map.domain.*
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.Reports
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val alertDialogState: Flow<AlertDialogState>
    val appMode: Flow<AppMode>
    val location: Flow<LocationState>
    val mapEvents: Flow<List<MapEvent>>
    val mapPreferences: Flow<GrodnoRoadsMapPreferences>

    val labels: Flow<Label>

    val alerts: Flow<List<Alert>>

    fun reportAction(latLng: LatLng, type: MapEventType)

    fun startLocationUpdates()
    fun stopLocationUpdates()

    fun onLocationDisabled()

    fun showMarkerInfoDialog(reports: Reports)
    fun closeDialog()
}