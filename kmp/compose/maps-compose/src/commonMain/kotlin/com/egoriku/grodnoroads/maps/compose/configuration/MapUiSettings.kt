package com.egoriku.grodnoroads.maps.compose.configuration

internal val DefaultMapUiSettings = MapUiSettings()

data class MapUiSettings(
    val compassEnabled: Boolean = false,
    val myLocationButtonEnabled: Boolean = false
)
