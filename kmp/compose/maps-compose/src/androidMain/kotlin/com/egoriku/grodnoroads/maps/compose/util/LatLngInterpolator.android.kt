package com.egoriku.grodnoroads.maps.compose.util

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import com.egoriku.grodnoroads.location.LatLng

actual fun animateMarker(
    start: LatLng,
    destination: LatLng,
    onInterpolated: (LatLng) -> Unit
) {
    ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1400
        interpolator = LinearInterpolator()
        addUpdateListener { animation ->
            try {
                val fraction = animation.animatedFraction
                val position = LatLngInterpolator.interpolate(fraction, start, destination)
                onInterpolated(position)
            } catch (ex: Exception) {
                // I don't care atm..
            }
        }
        start()
    }
}