package com.egoriku.grodnoroads.map.domain.model

sealed interface Alert {

    data class IncidentAlert(
        val mapEventType: MapEventType,
        val distance: Int,
        val messages: List<MessageItem>
    ) : Alert

    data class CameraAlert(
        val mapEventType: MapEventType,
        val distance: Int,
        val speedLimit: Int
    ) : Alert
}