@file:Suppress("NAME_SHADOWING")

package com.egoriku.grodnoroads.map.google.markers

import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.inScope
import com.egoriku.grodnoroads.maps.compose.rememberSimpleMarker
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.egoriku.grodnoroads.maps.core.extension.roundDistanceTo
import com.egoriku.grodnoroads.maps.core.util.LinearFixedInterpolator.Companion.linearFixedInterpolator
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.model.markerOptions

private const val ANIMATE_DISTANCE_THRESHOLD = 300

context(MapUpdater)
@Composable
fun NavigationMarker(
    appMode: AppMode,
    tag: String,
    position: StableLatLng,
    bearing: Float,
    icon: () -> BitmapDescriptor,
    rotation: Float,
) {
    val marker = rememberSimpleMarker(
        tag = tag,
        markerOptions = {
            markerOptions {
                position(position.value)
                icon(icon())
                zIndex(0f)
                anchor(0.5f, 0.5f)
                rotation(bearing - rotation)
            }
        },
    )

    LaunchedEffect(appMode) {
        marker.inScope {
            this.position = position.value
        }
    }

    LaunchedEffect(position) {
        marker.inScope {
            if (this.position roundDistanceTo position.value > ANIMATE_DISTANCE_THRESHOLD) {
                this.position = position.value
            } else {
                animateMarker(
                    destination = position.value,
                    bearing = bearing - rotation,
                    marker = this
                )
            }
        }
    }
}

/**
 * Method to animate marker to destination location
 * @param destination destination location (must contain bearing attribute, to ensure
 * marker rotation will work correctly)
 * @param marker marker to be animated
 */
private fun animateMarker(
    marker: Marker,
    destination: LatLng,
    bearing: Float
) {
    val startPosition = marker.position
    val startRotation = marker.rotation

    ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 1000
        interpolator = LinearInterpolator()
        addUpdateListener { animation ->
            try {
                val v = animation.animatedFraction
                val newPosition = linearFixedInterpolator.interpolate(v, startPosition, destination)
                marker.position = newPosition
                marker.rotation = computeRotation(v, startRotation, bearing)
            } catch (ex: Exception) {
                // I don't care atm..
            }
        }
        start()
    }
}

private fun computeRotation(fraction: Float, start: Float, end: Float): Float {
    // rotate start to 0
    val normalizeEnd = end - start

    val normalizedEndAbs = (normalizeEnd + 360) % 360

    // -1 = anticlockwise, 1 = clockwise
    val direction = (if (normalizedEndAbs > 180) -1 else 1).toFloat()

    val rotation = when {
        direction > 0 -> normalizedEndAbs
        else -> normalizedEndAbs - 360
    }
    val result = fraction * rotation + start
    return (result + 360) % 360
}