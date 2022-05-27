package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.extension.distanceTo
import com.egoriku.grodnoroads.screen.map.MapComponent.AlertMessage
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.StationaryCamera
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

private const val DISTANCE_RADIUS = 600
private const val MIN_DISTANCE = 20
private const val MIN_SPEED = 10

fun alertMessagesTransformation(): suspend (List<MapEvent>, LocationState) -> List<AlertMessage> =
    { mapEvents: List<MapEvent>, locationState: LocationState ->
        when {
            locationState.speed > MIN_SPEED -> makeAlertMessage(mapEvents, locationState)
            else -> emptyList()
        }
    }

private fun makeAlertMessage(
    mapEvents: List<MapEvent>,
    locationState: LocationState
) = mapEvents.mapNotNull { event ->
    val distance = computeDistance(
        eventLatLng = event.position,
        offsetLatLng = computeOffset(locationState),
        currentLatLnt = locationState.latLng
    )

    when (distance) {
        null -> null
        else -> when (event) {
            is StationaryCamera -> {
                AlertMessage(
                    distance = distance,
                    message = "",
                    speedLimit = event.speed,
                    mapEventType = event.mapEventType
                )
            }
            is MapEvent.UserActions -> {
                AlertMessage(
                    distance = distance,
                    message = event.message,
                    speedLimit = -1,
                    mapEventType = event.mapEventType
                )
            }
            is MapEvent.MobileCamera -> {
                AlertMessage(
                    distance = distance,
                    message = event.message,
                    speedLimit = -1,
                    mapEventType = event.mapEventType
                )
            }
        }
    }
}

private fun computeDistance(
    eventLatLng: LatLng,
    offsetLatLng: LatLng,
    currentLatLnt: LatLng
): Int? {
    val distanceBetweenOffsetAndEvent = eventLatLng distanceTo offsetLatLng

    return when {
        distanceBetweenOffsetAndEvent < DISTANCE_RADIUS -> {
            when (val distanceToEvent = currentLatLnt distanceTo eventLatLng) {
                in MIN_DISTANCE until DISTANCE_RADIUS -> distanceToEvent
                else -> null
            }
        }
        else -> null
    }
}

private fun computeOffset(locationState: LocationState) = SphericalUtil.computeOffset(
    locationState.latLng,
    DISTANCE_RADIUS.toDouble(),
    locationState.bearing.toDouble()
)
