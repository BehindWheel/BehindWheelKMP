package com.egoriku.grodnoroads.domain.model

import com.google.android.gms.maps.model.LatLng

data class UserPosition(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
) {
    companion object {
        val None = UserPosition(
            latLng = LatLng(0.0, 0.0),
            bearing = 0f,
            speed = 0
        )
    }
}