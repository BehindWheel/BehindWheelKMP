package com.egoriku.grodnoroads.map.foundation.map.configuration

import android.graphics.Point
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.CameraPositionState

@Composable
fun rememberCameraPositionValues(
    cameraPositionState: CameraPositionState,
    lastLocation: LastLocation
): CameraPositionValues {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val projection = cameraPositionState.projection

    val screenLocation by remember(projection) {
        mutableStateOf(
            projection?.toScreenLocation(lastLocation.latLng)?.apply {
                set(x, y - screenHeight / 3)
            } ?: Point()
        )
    }
    val fromScreenLocation by remember(projection, lastLocation) {
        mutableStateOf(projection?.fromScreenLocation(screenLocation) ?: lastLocation.latLng)
    }

    var lastBearing by remember { mutableStateOf(0.0f) }
    val directionBearing by remember(lastLocation) {
        mutableStateOf(
            when {
                lastLocation.speed > 10 -> {
                    lastBearing = lastLocation.bearing
                    lastLocation.bearing
                }

                else -> lastBearing
            }
        )
    }

    val computeHeading = SphericalUtil.computeHeading(lastLocation.latLng, fromScreenLocation)

    return remember(lastLocation, fromScreenLocation, directionBearing, computeHeading) {
        CameraPositionValues(
            initialLatLng = lastLocation.latLng,
            targetLatLngWithOffset = fromScreenLocation,
            bearing = directionBearing,
            markerRotation = (directionBearing - computeHeading).toFloat()
        )
    }
}

@Stable
data class CameraPositionValues(
    val initialLatLng: LatLng,
    val targetLatLngWithOffset: LatLng,
    val bearing: Float,
    val markerRotation: Float
)