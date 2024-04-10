package com.egoriku.grodnoroads.map.domain.util

import com.egoriku.grodnoroads.map.domain.model.Alert
import com.egoriku.grodnoroads.map.domain.model.Alert.CameraAlert
import com.egoriku.grodnoroads.map.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.maps.core.extension.computeOffset
import com.egoriku.grodnoroads.maps.core.extension.roundDistanceTo
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
                eventLatLng = event.position,
                offsetLatLng = computeOffset(
                    from = lastLocation.latLng,
                    distance = alertDistance.toDouble(),
                    heading = lastLocation.bearing.toDouble()
                ),
                currentLatLnt = lastLocation.latLng,
                distanceRadius = alertDistance
            )

            when (distance) {
                null -> null
                else -> when (event) {
                    is Camera -> {
                        if (event.angle == -1f) {
                            return@mapNotNull CameraAlert(
                                id = event.id,
                                distance = distance,
                                // TODO: handle car type
                                speedLimit = event.speedCar,
                                cameraType = event.cameraType
                            )
                        }

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
    val distanceBetweenOffsetAndEvent = eventLatLng roundDistanceTo offsetLatLng

    return when {
        distanceBetweenOffsetAndEvent < distanceRadius -> {
            when (val distanceToEvent = currentLatLnt roundDistanceTo eventLatLng) {
                in MIN_DISTANCE until distanceRadius -> distanceToEvent
                else -> null
            }
        }

        else -> null
    }
}