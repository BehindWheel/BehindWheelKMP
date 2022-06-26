package com.egoriku.grodnoroads.screen.map.domain

import com.google.android.gms.maps.model.LatLng

data class LocationState(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
) {

    companion object {
        val EmptyLatLng = LatLng(0.0, 0.0)

        val None = LocationState(
            latLng = EmptyLatLng,
            bearing = 0f,
            speed = 0
        )
    }
}