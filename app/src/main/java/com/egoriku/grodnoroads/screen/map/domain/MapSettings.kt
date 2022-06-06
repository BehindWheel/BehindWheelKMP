package com.egoriku.grodnoroads.screen.map.domain

data class MapSettings(
    val isTrafficEnabled: Boolean
) {
    companion object {
        val Default = MapSettings(
            isTrafficEnabled = false,
        )
    }
}
