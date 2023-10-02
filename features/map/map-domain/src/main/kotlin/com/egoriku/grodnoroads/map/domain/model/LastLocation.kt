package com.egoriku.grodnoroads.map.domain.model

import com.egoriku.grodnoroads.maps.core.StableLatLng

data class LastLocation(
    val latLng: StableLatLng,
    val bearing: Float,
    val speed: Int
) {
    companion object {
        val UNKNOWN_LOCATION = StableLatLng(0.0, 0.0)

        val None = LastLocation(
            latLng = UNKNOWN_LOCATION,
            bearing = -1f,
            speed = 0
        )
    }
}