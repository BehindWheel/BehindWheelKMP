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
import com.egoriku.grodnoroads.maps.compose.util.LatLngInterpolator
import com.egoriku.grodnoroads.maps.core.extension.roundDistanceTo
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.model.markerOptions

private const val ANIMATE_DISTANCE_THRESHOLD = 300

context(MapUpdater)
@Composable
fun NavigationMarker(
    appMode: AppMode,
    position: LatLng,
    bearing: Float,
    icon: () -> BitmapDescriptor,
    rotation: Float,
) {
    val marker = rememberSimpleMarker(
        markerOptions = {
            markerOptions {
                position(position)
                icon(icon())
                zIndex(0f)
                anchor(0.5f, 0.5f)
                rotation(bearing - rotation)
            }
        },
    )

    LaunchedEffect(appMode) {
        marker.inScope {
            this.position = position
        }
    }

    LaunchedEffect(position) {
        marker.inScope {
            if (this.position roundDistanceTo position > ANIMATE_DISTANCE_THRESHOLD) {
                this.position = position
            } else {
                animateMarker(
                    start = this.position,
                    destination = position,
                    onInterpolated = {
                        this.position = it
                    }
                )
            }
        }
    }
}

/**
 * Interpolate between start and end location
 */
private fun animateMarker(
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