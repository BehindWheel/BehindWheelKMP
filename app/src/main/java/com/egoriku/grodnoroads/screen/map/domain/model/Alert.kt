package com.egoriku.grodnoroads.screen.map.domain.model

import com.egoriku.grodnoroads.map.domain.model.MapEventType
import com.egoriku.grodnoroads.map.domain.model.MessageItem

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