package com.egoriku.grodnoroads.mapswrapper

internal val DefaultMapUiSettings = MapUiSettings()

data class MapUiSettings(
    val compassEnabled: Boolean = false,
    val myLocationButtonEnabled: Boolean = false,
)
