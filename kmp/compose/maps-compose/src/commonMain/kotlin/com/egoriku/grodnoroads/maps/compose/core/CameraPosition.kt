package com.egoriku.grodnoroads.maps.compose.core

import com.egoriku.grodnoroads.location.LatLng

expect class PlatformCameraPosition

expect class CameraPosition(platformCameraPosition: PlatformCameraPosition) {

    constructor(
        target: LatLng,
        zoom: Float
    )
}
