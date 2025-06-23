package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.MapType

@Stable
data class MapConfig(
    val zoomLevel: Float,
    val trafficJanOnMap: Boolean,
    val mapType: MapType,
    val keepScreenOn: Boolean,
    val alertsEnabled: Boolean,
    val alertRadius: Int,
    val isChooseInDriveMode: Boolean
) {
    companion object {
        val EMPTY = MapConfig(
            zoomLevel = -1f,
            trafficJanOnMap = false,
            mapType = MapType.Normal,
            keepScreenOn = false,
            alertsEnabled = false,
            alertRadius = -1,
            isChooseInDriveMode = false
        )
    }
}
