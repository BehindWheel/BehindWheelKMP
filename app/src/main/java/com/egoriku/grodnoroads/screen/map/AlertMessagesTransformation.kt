package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.extension.distanceTo
import com.egoriku.grodnoroads.screen.map.MapComponent.AlertMessage
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.StationaryCamera
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

private const val DISTANCE_RADIUS = 600

fun alertMessagesTransformation(): suspend (List<MapEvent>, LocationState) -> List<AlertMessage> =
    { mapEvents: List<MapEvent>, locationState: LocationState ->
        mapEvents.mapNotNull { mapEvent ->
            val distance = computeDistance(
                eventLatLng = mapEvent.position,
                offsetLatLng = computeOffset(locationState),
                currentLatLnt = locationState.latLng
            )

            when (distance) {
                null -> null
                else -> when (mapEvent) {
                    is StationaryCamera -> {
                        AlertMessage(
                            distance = distance,
                            message = "",
                            speedLimit = mapEvent.speed,
                            eventType = mapEvent.eventType
                        )
                    }
                    is MapEvent.UserActions -> {
                        AlertMessage(
                            distance = distance,
                            message = mapEvent.message,
                            speedLimit = -1,
                            eventType = mapEvent.eventType
                        )
                    }
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
            val distanceToEvent = currentLatLnt distanceTo eventLatLng

            when {
                distanceToEvent < DISTANCE_RADIUS -> distanceToEvent
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
