package com.egoriku.grodnoroads.map.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.map.domain.model.MapEventType.MobileCamera
import com.egoriku.grodnoroads.map.domain.model.MapEventType.StationaryCamera
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface MapEvent {

    val position: LatLng
    val mapEventType: MapEventType

    data class StationaryCamera(
        val message: String,
        val speed: Int,
        override val position: LatLng,
        override val mapEventType: MapEventType = StationaryCamera
    ) : MapEvent

    data class Reports(
        val markerMessage: String,
        val dialogTitle: String,
        val messages: ImmutableList<MessageItem>,
        override val position: LatLng,
        override val mapEventType: MapEventType
    ) : MapEvent

    data class MobileCamera(
        val message: String,
        val speed: Int,
        override val position: LatLng,
        override val mapEventType: MapEventType = MobileCamera
    ) : MapEvent
}