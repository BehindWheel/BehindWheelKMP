package com.egoriku.grodnoroads.maps.compose.configuration

import androidx.compose.runtime.Stable

internal val DefaultMapProperties = MapProperties()

@Stable
data class MapProperties(
    val isMyLocationEnabled: Boolean = false,
    val isTrafficEnabled: Boolean = false,
    val mapType: MapType = MapType.Normal,
    val mapColor: MapColor = MapColor.System,
    val maxZoomPreference: Float = 21.0f,
    val minZoomPreference: Float = 3.0f
)
