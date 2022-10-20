package com.egoriku.grodnoroads.map.domain.model

import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.Style

data class MapConfig(
    val zoomLevel: Float,
    val googleMapStyle: Style,
    val trafficJanOnMap: Boolean
) {
    companion object {
        val EMPTY = MapConfig(
            zoomLevel = -1f,
            googleMapStyle = Style.Unknown,
            trafficJanOnMap = false
        )
    }
}
