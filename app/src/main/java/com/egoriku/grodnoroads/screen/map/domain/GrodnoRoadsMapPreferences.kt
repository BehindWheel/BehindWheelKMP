package com.egoriku.grodnoroads.screen.map.domain

data class GrodnoRoadsMapPreferences(
    val isTrafficEnabled: Boolean
) {
    companion object {
        val Default = GrodnoRoadsMapPreferences(
            isTrafficEnabled = false,
        )
    }
}
