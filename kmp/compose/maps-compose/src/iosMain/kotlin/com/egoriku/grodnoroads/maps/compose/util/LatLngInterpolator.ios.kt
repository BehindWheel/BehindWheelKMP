package com.egoriku.grodnoroads.maps.compose.util

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.core.Marker
import platform.QuartzCore.CATransaction

actual fun animateMarker(
    marker: Marker,
    start: LatLng,
    destination: LatLng,
    onInterpolated: (LatLng) -> Unit
) {
    CATransaction.begin()
    CATransaction.setAnimationDuration(1.4)
    marker.position = destination.cValue
    CATransaction.commit()
}
