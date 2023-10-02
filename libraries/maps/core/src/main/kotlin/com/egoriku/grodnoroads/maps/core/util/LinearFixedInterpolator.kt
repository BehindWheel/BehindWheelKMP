package com.egoriku.grodnoroads.maps.core.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.abs
import kotlin.math.sign

class LinearFixedInterpolator {
    companion object {
        val linearFixedInterpolator = LinearFixedInterpolator()
    }

    fun interpolate(fraction: Float, a: LatLng, b: LatLng): LatLng {
        val lat = (b.latitude - a.latitude) * fraction + a.latitude
        var lngDelta = b.longitude - a.longitude

        // Take the shortest path across the 180th meridian.
        if (abs(lngDelta) > 180) {
            lngDelta -= sign(lngDelta) * 360
        }
        val lng = lngDelta * fraction + a.longitude
        return LatLng(lat, lng)
    }
}