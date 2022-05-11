package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.EventType
import com.egoriku.grodnoroads.domain.model.EventType.MobileCamera
import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val appMode: Flow<AppMode>
    val location: Flow<LocationState>
    val mapEvents: Flow<List<MapEvent>>

    val labels: Flow<Label>

    val alertMessages: Flow<List<AlertMessage>>

    fun reportAction(latLng: LatLng, type: EventType)

    fun startLocationUpdates()
    fun stopLocationUpdates()

    fun onLocationDisabled()

    data class AlertMessage(
        val distance: Int,
        val message: String,
        val speedLimit: Int,
        val eventType: EventType
    )

    sealed interface MapEvent {

        val position: LatLng
        val eventType: EventType

        data class StationaryCamera(
            val message: String,
            val speed: Int,
            override val position: LatLng,
            override val eventType: EventType
        ) : MapEvent

        data class UserActions(
            val time: String,
            val message: String,
            override val position: LatLng,
            override val eventType: EventType
        ) : MapEvent

        data class MobileCamera(
            val message: String,
            override val position: LatLng,
            override val eventType: EventType = MobileCamera
        ) : MapEvent
    }
}