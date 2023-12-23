package com.egoriku.grodnoroads.map.domain.model

import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.Style

data class MapConfig(
    val zoomLevel: Float,
    val googleMapStyle: Style,
    val trafficJanOnMap: Boolean,
    val keepScreenOn: Boolean,
    val alertsEnabled: Boolean,
    val alertRadius: Int,
) {
    companion object {
        val EMPTY = MapConfig(
            zoomLevel = -1f,
            googleMapStyle = Style.Unknown,
            trafficJanOnMap = false,
            keepScreenOn = false,
            alertsEnabled = false,
            alertRadius = -1
        )
    }
}
