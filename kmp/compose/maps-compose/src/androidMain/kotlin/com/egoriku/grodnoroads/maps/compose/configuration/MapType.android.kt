package com.egoriku.grodnoroads.maps.compose.configuration

import com.google.android.gms.maps.GoogleMap

fun MapType.toAndroidMapType() = when (this) {
    MapType.Normal -> GoogleMap.MAP_TYPE_NORMAL
    MapType.Satellite -> GoogleMap.MAP_TYPE_SATELLITE
    MapType.Hybrid -> GoogleMap.MAP_TYPE_HYBRID
}