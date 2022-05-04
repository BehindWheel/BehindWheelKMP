package com.egoriku.grodnoroads.foundation.map

import android.graphics.Point
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import com.egoriku.grodnoroads.domain.model.LocationState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.google.maps.android.compose.CameraPositionState

@Composable
fun rememberCameraPositionValues(
    cameraPositionState: CameraPositionState,
    locationState: LocationState
): CameraPositionValues {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    val projection= cameraPositionState.projection

    val screenLocation by remember(projection) {
        mutableStateOf(
            projection?.toScreenLocation(locationState.latLng)?.apply {
                set(x, y - screenHeight / 3)
            } ?: Point()
        )
    }
    val fromScreenLocation by remember(projection) {
        mutableStateOf(projection?.fromScreenLocation(screenLocation) ?: locationState.latLng)
    }

    val directionBearing by remember(locationState) {
        mutableStateOf(
            when {
                locationState.speed > 10 -> locationState.bearing
                else -> 0.0f
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