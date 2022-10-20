package com.egoriku.grodnoroads.location

import com.google.android.gms.maps.model.LatLng

data class LocationInfo(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
)
