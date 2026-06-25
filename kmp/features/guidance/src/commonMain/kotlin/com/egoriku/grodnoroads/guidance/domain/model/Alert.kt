package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.models.MapEventType

@Stable
sealed interface Alert {
    val id: String

    @Immutable
    data class IncidentAlert(
        val mapEventType: MapEventType,
        val distance: Int,
        val messages: List<MessageItem>,
        override val id: String
    ) : Alert

    @Immutable
    data class CameraAlert(
        val cameraType: CameraType,
        val distance: Int,
        val speedLimit: Int,
        override val id: String
    ) : Alert
}
