package com.egoriku.grodnoroads.screen.map.domain

import com.google.android.gms.maps.model.LatLng

sealed interface MapEvent {

    val position: LatLng
    val mapEventType: MapEventType

    data class StationaryCamera(
        val message: String,
        val speed: Int,
        override val position: LatLng,
        override val mapEventType: MapEventType = MapEventType.StationaryCamera
    ) : MapEvent

    data class Reports(
        val shortMessage: String,
        val messages: List<MessageItem>,
        override val position: LatLng,
        override val mapEventType: MapEventType
    ) : MapEvent

    data class MobileCamera(
        val message: String,
        val speed: Int,
        override val position: LatLng,
        override val mapEventType: MapEventType = MapEventType.MobileCamera
    ) : MapEvent
}