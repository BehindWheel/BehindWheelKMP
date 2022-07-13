package com.egoriku.grodnoroads.screen.map.domain

import com.google.android.gms.maps.model.LatLng

data class LocationState(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
) {

    companion object {
        val GrodnoLatLng = LatLng(53.6687765, 23.8212226)

        val None = LocationState(
            latLng = GrodnoLatLng,
            bearing = 0f,
            speed = 0
        )
    }
}