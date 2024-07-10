package com.egoriku.grodnoroads.maps.compose.util

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.core.Marker
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

expect fun animateMarker(
    marker: Marker,
    start: LatLng,
    destination: LatLng,
    onInterpolated: (LatLng) -> Unit
)