package com.egoriku.grodnoroads.map.domain.util

import com.egoriku.grodnoroads.map.domain.model.Alert
import com.egoriku.grodnoroads.map.domain.model.LastLocation

private const val GEO_SPEED_ACCURACY = 4
private const val ALLOWED_OVER_SPEED = 5

fun overSpeedTransformation(): suspend (
    List<Alert>, LastLocation
) -> Int = { alerts, lastLocation ->
    val cameraEvents = alerts.filterIsInstance<Alert.CameraAlert>()

    if (cameraEvents.isEmpty()) {
        -1
    } else {
        val speedLimit = cameraEvents.first().speedLimit
        val currentSpeed = lastLocation.speed

        if (currentSpeed + GEO_SPEED_ACCURACY + ALLOWED_OVER_SPEED >= speedLimit) {
            speedLimit
        } else {
            -1
        }
    }
}