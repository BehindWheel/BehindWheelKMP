package com.egoriku.grodnoroads.map.domain.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface Alert {

    data class IncidentAlert(
        val mapEventType: MapEventType,
        val distance: Int,
        val messages: ImmutableList<MessageItem>
    ) : Alert

    data class CameraAlert(
        val mapEventType: MapEventType,
        val distance: Int,
        val speedLimit: Int
    ) : Alert
}