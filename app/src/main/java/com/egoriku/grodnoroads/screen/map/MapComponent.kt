package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.domain.model.MapEventType
import com.egoriku.grodnoroads.domain.model.MapEventType.MobileCamera
import com.egoriku.grodnoroads.domain.model.MapEventType.StationaryCamera
import com.egoriku.grodnoroads.domain.model.Source
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val appMode: Flow<AppMode>
    val location: Flow<LocationState>
    val mapEvents: Flow<List<MapEvent>>

    val labels: Flow<Label>

    val alertMessages: Flow<List<AlertMessage>>

    fun reportAction(latLng: LatLng, type: MapEventType)

    fun startLocationUpdates()
    fun stopLocationUpdates()

    fun onLocationDisabled()

    data class AlertMessage(
        val distance: Int,
        val message: String,
        val speedLimit: Int,
        val mapEventType: MapEventType
    )

    sealed interface MapEvent {

        val position: LatLng
        val mapEventType: MapEventType

        data class StationaryCamera(
            val message: String,
            val speed: Int,
            override val position: LatLng,
            override val mapEventType: MapEventType = StationaryCamera
        ) : MapEvent

        data class UserActions(
            val shortMessage: String,
            val messages: List<MessageItem>,
            override val position: LatLng,
            override val mapEventType: MapEventType
        ) : MapEvent

        data class MobileCamera(
            val message: String,
            override val position: LatLng,
            override val mapEventType: MapEventType = MobileCamera
        ) : MapEvent
    }

    data class MessageItem(
        val message: String,
        val source: Source
    )
}