package com.egoriku.grodnoroads.maps.compose.util

import com.egoriku.grodnoroads.location.LatLng

actual fun animateMarker(
    start: LatLng,
    destination: LatLng,
    onInterpolated: (LatLng) -> Unit
) {
    // TODO: create marker animation
    onInterpolated(destination)
}
