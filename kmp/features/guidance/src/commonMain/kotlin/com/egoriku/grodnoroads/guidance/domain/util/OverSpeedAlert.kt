package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation

private const val ALLOWED_OVER_SPEED = 2
private const val DOUBLE_OVER_SPEED_DISTANCE = 150

internal sealed interface OverSpeedAlert {
    data object None : OverSpeedAlert
    data class Regular(val speedLimit: Int) : OverSpeedAlert
    data class Double(val cameraId: String, val speedLimit: Int) : OverSpeedAlert
}

internal fun overSpeedTransformation(): suspend (List<Alert>, LastLocation) -> OverSpeedAlert {
    return { alerts, lastLocation ->
        val currentSpeed = lastLocation.speed

        val nearestOverSpeedCamera = alerts
            .filterIsInstance<Alert.CameraAlert>()
            .filter { currentSpeed >= it.speedLimit + ALLOWED_OVER_SPEED }
            .minByOrNull { it.distance }

        when {
            nearestOverSpeedCamera == null -> OverSpeedAlert.None
            nearestOverSpeedCamera.distance <= DOUBLE_OVER_SPEED_DISTANCE ->
                OverSpeedAlert.Double(
                    cameraId = nearestOverSpeedCamera.id,
                    speedLimit = nearestOverSpeedCamera.speedLimit
                )
            else -> OverSpeedAlert.Regular(speedLimit = nearestOverSpeedCamera.speedLimit)
        }
    }
}
