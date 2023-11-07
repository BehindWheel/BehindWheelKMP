package com.egoriku.grodnoroads.maps.core.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.abs
import kotlin.math.sign

open class LatLngInterpolator {
    companion object Default : LatLngInterpolator()

    fun interpolate(fraction: Float, a: LatLng, b: LatLng): LatLng {
        val lat = (b.latitude - a.latitude) * fraction + a.latitude
        var delta = b.longitude - a.longitude

        // Take the shortest path across the 180th meridian.
        if (abs(delta) > 180) {
            delta -= sign(delta) * 360
        }
        val lng = delta * fraction + a.longitude
        return LatLng(lat, lng)
    }
}