package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.calc.distanceTo
import com.egoriku.grodnoroads.location.calc.headingTo
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sin

internal const val ROAD_HALF_WIDTH = 40.0 // meters

/**
 * Returns true if the user is within the camera's road corridor
 * (within [ROAD_HALF_WIDTH] meters of the camera's road axis).
 *
 * The cross-track distance is computed as:
 *   crossTrack = distance(camera, user) × sin(headingToUser − cameraAngle)
 *
 * If |crossTrack| > [ROAD_HALF_WIDTH], the user is on a neighboring road.
 */
fun isUserOnCameraRoad(
    cameraLatLng: LatLng,
    cameraAngle: Float,
    userLatLng: LatLng
): Boolean {
    val distToUser = cameraLatLng distanceTo userLatLng
    if (distToUser < 1.0) return true // user is essentially at the camera

    val headingToUser = cameraLatLng headingTo userLatLng // range: -180..180

    var angleDiff = headingToUser - cameraAngle.toDouble()
    // Normalize to [-180, 180]
    angleDiff = ((angleDiff % 360.0) + 540.0) % 360.0 - 180.0

    val crossTrackDistance = distToUser * sin(angleDiff * PI / 180.0)
    return abs(crossTrackDistance) <= ROAD_HALF_WIDTH
}
