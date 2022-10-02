package com.egoriku.grodnoroads.screen.map.domain.model

data class MapConfig(
    val zoomLevel: Float
) {
    companion object {
        val EMPTY = MapConfig(
            zoomLevel = -1f
        )
    }
}
