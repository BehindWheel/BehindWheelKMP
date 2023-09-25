package com.egoriku.grodnoroads.map.domain.util

import com.egoriku.grodnoroads.extensions.util.computeOffset
import com.egoriku.grodnoroads.extensions.util.distanceTo
import com.egoriku.grodnoroads.map.domain.model.*
import com.egoriku.grodnoroads.map.domain.model.Alert.CameraAlert
import com.egoriku.grodnoroads.map.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

private const val MIN_DISTANCE = 20
private const val MIN_SPEED = 10

private val emptyList = persistentListOf<Alert>()

fun alertMessagesTransformation(): suspend (List<MapEvent>, LastLocation, MapConfig, AppMode) -> ImmutableList<Alert> =
    { mapEvents, lastLocation, config, appMode ->
        when (lastLocation) {
            LastLocation.None -> emptyList
            else -> when {
                !config.alertsEnabled && appMode != AppMode.Drive -> emptyList
                lastLocation.speed > MIN_SPEED -> makeAlertMessage(
                    mapEvents = mapEvents,
                    lastLocation = lastLocation,
                    alertDistance = config.alertRadius
                )

                else -> emptyList
            }
        }
    }

private fun makeAlertMessage(
    mapEvents: List<MapEvent>,
    lastLocation: LastLocation,
    alertDistance: Int
) = mapEvents.mapNotNull { event ->
    when (lastLocation) {
        LastLocation.None -> null
        else -> {
            val distance = computeDistance(
                eventLatLng = event.position.value,
                offsetLatLng = computeOffset(
                    from = lastLocation.latLng.value,
                    distance = alertDistance.toDouble(),
                    heading = lastLocation.bearing.toDouble()
                ),
                currentLatLnt = lastLocation.latLng.value,
                distanceRadius = alertDistance
            )

            when (distance) {
                null -> null
                else -> when (event) {
                    is Camera.StationaryCamera -> {
                        val inRange = isAngleInRange(
                            cameraAngle = event.angle,
                            bidirectional = event.bidirectional,
                            bearing = lastLocation.bearing
                        )

                        if (inRange) {
                            CameraAlert(
                                id = event.id,
                                distance = distance,
                                // TODO: handle car type
                                speedLimit = event.speedCar,
                                cameraType = event.cameraType
                            )
                        } else null
                    }

                    is Reports -> {
                        IncidentAlert(
                            // TODO: in future use id from Group
                            id = event.markerMessage,
                            distance = distance,
                            messages = event.messages,
                            mapEventType = event.mapEventType
                        )
                    }

                    is Camera.MobileCamera -> {
                        CameraAlert(
                            id = event.id,
                            distance = distance,
                            speedLimit = event.speedCar,
                            cameraType = event.cameraType
                        )
                    }

                    is Camera.MediumSpeedCamera -> CameraAlert(
                        id = event.id,
                        distance = distance,
                        speedLimit = event.speedCar,
                        cameraType = event.cameraType
                    )
                }
            }
        }
    }
}.toImmutableList()

private fun computeDistance(
    eventLatLng: LatLng,
    offsetLatLng: LatLng,
    currentLatLnt: LatLng,
    distanceRadius: Int
): Int? {
    val distanceBetweenOffsetAndEvent = eventLatLng distanceTo offsetLatLng

    return when {
        distanceBetweenOffsetAndEvent < distanceRadius -> {
            when (val distanceToEvent = currentLatLnt distanceTo eventLatLng) {
                in MIN_DISTANCE until distanceRadius -> distanceToEvent
                else -> null
            }
        }

        else -> null
    }
}