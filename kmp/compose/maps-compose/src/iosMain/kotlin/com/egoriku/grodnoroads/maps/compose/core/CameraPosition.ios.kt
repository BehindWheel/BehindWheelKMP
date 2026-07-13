package com.egoriku.grodnoroads.maps.compose.core

import com.egoriku.grodnoroads.location.LatLng
import kotlinx.cinterop.ExperimentalForeignApi
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSCameraPosition

@OptIn(ExperimentalForeignApi::class)
actual typealias PlatformCameraPosition = GMSCameraPosition

@OptIn(ExperimentalForeignApi::class)
actual class CameraPosition actual constructor(
    val platformCameraPosition: PlatformCameraPosition
) {
    actual constructor(
        target: LatLng,
        zoom: Float,
        bearing: Float,
        tilt: Float
    ) : this(
        platformCameraPosition = GMSCameraPosition.cameraWithTarget(
            target = target.cValue,
            zoom = zoom,
            bearing = bearing.toDouble(),
            viewingAngle = tilt.toDouble()
        )
    )
}
