package com.egoriku.grodnoroads.screen.map.domain

import com.google.android.gms.maps.model.LatLng

data class LocationState(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
) {
    companion object {
        val None = LocationState(
            latLng = LatLng(0.0, 0.0),
            bearing = 0f,
            speed = 0
        )
    }
}