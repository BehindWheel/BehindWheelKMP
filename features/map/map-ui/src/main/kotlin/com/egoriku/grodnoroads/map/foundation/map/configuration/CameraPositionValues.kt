@file:Suppress("USELESS_ELVIS")

package com.egoriku.grodnoroads.map.foundation.map.configuration

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

private var lastBearing = 0.0f

private fun LatLng.isValid(): Boolean {
    return latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180
}

fun calculateCameraPositionValues(
    isMapLoaded: Boolean,
    screenHeight: Int,
    projection: Projection?,
    lastLocation: LastLocation
): CameraPositionValues {
    if (!isMapLoaded || projection == null || !lastLocation.latLng.isValid()) {
        return CameraPositionValues(
            initialLatLng = lastLocation.latLng,
            targetLatLngWithOffset = lastLocation.latLng,
            bearing = 0f,
            markerRotation = 0f
        )
    }
    val screenLocation = projection.toScreenLocation(lastLocation.latLng).apply {
        set(x, y - screenHeight / 3)
    }

    val fromScreenLocation = projection.fromScreenLocation(screenLocation)
        ?: return CameraPositionValues(
            initialLatLng = lastLocation.latLng,
            targetLatLngWithOffset = lastLocation.latLng,
            bearing = 0f,
            markerRotation = 0f
        )

    val directionBearing =
        when {
            lastLocation.speed > 10 -> {
                lastBearing = lastLocation.bearing
                lastLocation.bearing
            }

            else -> lastBearing
        }

    val computeHeading = SphericalUtil.computeHeading(lastLocation.latLng, fromScreenLocation)

    return CameraPositionValues(
        initialLatLng = lastLocation.latLng,
        targetLatLngWithOffset = fromScreenLocation,
        bearing = directionBearing,
        markerRotation = (directionBearing - computeHeading).toFloat()
    )
}

@Stable
data class CameraPositionValues(
    val initialLatLng: LatLng,
    val targetLatLngWithOffset: LatLng,
    val bearing: Float,
    val markerRotation: Float
)