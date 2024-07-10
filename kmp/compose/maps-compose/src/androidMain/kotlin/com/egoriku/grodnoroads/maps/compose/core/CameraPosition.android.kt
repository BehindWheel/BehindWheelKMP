package com.egoriku.grodnoroads.maps.compose.core

import com.egoriku.grodnoroads.location.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.ktx.model.cameraPosition

actual typealias PlatformCameraPosition = CameraPosition

actual class CameraPosition actual constructor(
   val platformCameraPosition: PlatformCameraPosition
) {
    actual constructor(
        target: LatLng,
        zoom: Float
    ) : this(
        platformCameraPosition = cameraPosition {
            target(target.platform)
            zoom(zoom)
        }
    )
}