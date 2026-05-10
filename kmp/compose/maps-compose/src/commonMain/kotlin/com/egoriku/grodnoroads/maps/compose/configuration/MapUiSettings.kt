package com.egoriku.grodnoroads.maps.compose.configuration

import androidx.compose.runtime.Stable

internal val DefaultMapUiSettings = MapUiSettings()

@Stable
data class MapUiSettings(
    val compassEnabled: Boolean = false,
    val myLocationButtonEnabled: Boolean = false
)
