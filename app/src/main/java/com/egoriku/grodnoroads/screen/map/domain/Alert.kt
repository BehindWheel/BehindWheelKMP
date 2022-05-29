package com.egoriku.grodnoroads.screen.map.domain

import com.egoriku.grodnoroads.domain.model.MapEventType
import com.egoriku.grodnoroads.domain.model.Source

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

data class MessageItem(
    val message: String,
    val source: Source
)