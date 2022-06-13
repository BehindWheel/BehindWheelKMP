package com.egoriku.grodnoroads.foundation.map

import android.graphics.Point
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.CameraPositionState

@Composable
fun rememberCameraPositionValues(
    cameraPositionState: CameraPositionState,
    locationState: LocationState
): CameraPositionValues {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val projection = cameraPositionState.projection

    val screenLocation by remember(projection) {
        mutableStateOf(
            projection?.toScreenLocation(locationState.latLng)?.apply {
                set(x, y - screenHeight / 3)
            } ?: Point()
        )
    }
    val fromScreenLocation by remember(projection, locationState) {
        mutableStateOf(projection?.fromScreenLocation(screenLocation) ?: locationState.latLng)
    }

    var lastBearing by remember { mutableStateOf(0.0f) }
    val directionBearing by remember(locationState) {
        mutableStateOf(
            when {
                locationState.speed > 10 -> {
                    lastBearing = locationState.bearing
                    locationState.bearing
                }
                else -> lastBearing
            }
        )
    }

    val computeHeading = SphericalUtil.computeHeading(locationState.latLng, fromScreenLocation)

    return CameraPositionValues(
        targetLatLng = fromScreenLocation,
        bearing = directionBearing,
        markerRotation = (directionBearing - computeHeading).toFloat()
    )
}

data class CameraPositionValues(
    val targetLatLng: LatLng,
    val bearing: Float,
    val markerRotation: Float
)