package com.egoriku.grodnoroads.map.domain.model

import com.egoriku.grodnoroads.mapswrapper.core.StableLatLng
import com.egoriku.grodnoroads.mapswrapper.core.asStable
import com.google.android.gms.maps.model.LatLng

data class LastLocation(
    val latLng: StableLatLng,
    val bearing: Float,
    val speed: Int
) {
    companion object {
        val UNKNOWN_LOCATION = LatLng(0.0, 0.0).asStable()

        val None = LastLocation(
            latLng = UNKNOWN_LOCATION,
            bearing = -1f,
            speed = 0
        )
    }
}