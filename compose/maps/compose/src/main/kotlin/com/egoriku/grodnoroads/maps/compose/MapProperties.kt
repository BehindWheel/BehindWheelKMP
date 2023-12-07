package com.egoriku.grodnoroads.maps.compose

import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.model.MapStyleOptions

internal val DefaultMapProperties = MapProperties()

data class MapProperties(
    val isMyLocationEnabled: Boolean = false,
    val isTrafficEnabled: Boolean = false,
    val mapStyleOptions: MapStyleOptions? = null,
    val mapType: MapType = MapType.NORMAL,
    val maxZoomPreference: Float = 21.0f,
    val minZoomPreference: Float = 3.0f,
)

@Immutable
enum class MapType(val value: Int) {
    NONE(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE),
    NORMAL(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL),
    SATELLITE(com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE),
    TERRAIN(com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN),
    HYBRID(com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID)
}

