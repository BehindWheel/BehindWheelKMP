package com.egoriku.grodnoroads.guidance.domain.model.event

import com.egoriku.grodnoroads.guidance.domain.model.CameraType
import com.egoriku.grodnoroads.shared.models.MapEventType

sealed interface AlertEvent {
    data class CameraLimit(
        val id: String,
        val speedLimit: Int,
        val cameraType: CameraType
    ) : AlertEvent

    data class IncidentAlert(
        val id: String,
        val mapEventType: MapEventType
    ) : AlertEvent

    data object OverSpeed : AlertEvent

    data class VolumeChange(
        val volume: Float,
        val loudness: Int
    ) : AlertEvent
}
