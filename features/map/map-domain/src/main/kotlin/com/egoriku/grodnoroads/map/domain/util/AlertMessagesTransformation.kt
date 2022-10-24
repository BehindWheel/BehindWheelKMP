package com.egoriku.grodnoroads.map.domain.util

import com.egoriku.grodnoroads.extensions.util.computeOffset
import com.egoriku.grodnoroads.extensions.util.distanceTo
import com.egoriku.grodnoroads.map.domain.model.Alert
import com.egoriku.grodnoroads.map.domain.model.Alert.CameraAlert
import com.egoriku.grodnoroads.map.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEvent.*
import com.google.android.gms.maps.model.LatLng

private const val MIN_DISTANCE = 20
private const val MIN_SPEED = 10

fun alertMessagesTransformation(): suspend (
    List<MapEvent>,
    LastLocation,
    Int
) -> List<Alert> =
    { mapEvents: List<MapEvent>,
      lastLocation: LastLocation,
      alertDistance: Int ->
        when (lastLocation) {
            LastLocation.None -> emptyList()
            else -> when {
                lastLocation.speed > MIN_SPEED -> makeAlertMessage(
                    mapEvents = mapEvents,
                    lastLocation = lastLocation,
                    distanceRadius = alertDistance
                )
                else -> emptyList()
            }
        }
    }

private fun makeAlertMessage(
    mapEvents: List<MapEvent>,
    lastLocation: LastLocation,
    distanceRadius: Int
) = mapEvents.mapNotNull { event ->
    when (lastLocation) {
        LastLocation.None -> null
        else -> {
            val distance = computeDistance(
                eventLatLng = event.position,
                offsetLatLng = computeOffset(
                    from = lastLocation.latLng,
                    distance = distanceRadius.toDouble(),
                    heading = lastLocation.bearing.toDouble()
                ),
                currentLatLnt = lastLocation.latLng,
                distanceRadius = distanceRadius
            )

            when (distance) {
                null -> null
                else -> when (event) {
                    is StationaryCamera -> {
                        CameraAlert(
                            distance = distance,
                            speedLimit = event.speed,
                            mapEventType = event.mapEventType
                        )
                    }

                    is Reports -> {
                        IncidentAlert(
                            distance = distance,
                            messages = event.messages,
                            mapEventType = event.mapEventType
                        )
                    }

                    is MobileCamera -> {
                        CameraAlert(
                            distance = distance,
                            speedLimit = event.speed,
                            mapEventType = event.mapEventType
                        )
                    }
                }
            }
        }
    }
}

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