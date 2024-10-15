package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable

@Stable
data class MapConfig(
    val zoomLevel: Float,
    val trafficJanOnMap: Boolean,
    val keepScreenOn: Boolean,
    val alertsEnabled: Boolean,
    val alertRadius: Int,
    val isChooseInDriveMode: Boolean
) {
    companion object {
        val EMPTY = MapConfig(
            zoomLevel = -1f,
            trafficJanOnMap = false,
            keepScreenOn = false,
            alertsEnabled = false,
            alertRadius = -1,
            isChooseInDriveMode = false
        )
    }
}
