package com.egoriku.grodnoroads.map.domain.model

import com.google.android.gms.maps.model.LatLng

data class LastLocation(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
) {
    companion object {
        val None = LastLocation(
            latLng = LatLng(0.0, 0.0),
            bearing = -1f,
            speed = 0
        )
    }
}