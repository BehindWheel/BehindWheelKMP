package com.egoriku.grodnoroads.screen.map.domain.component.util

import com.egoriku.grodnoroads.extensions.util.computeOffset
import com.egoriku.grodnoroads.extensions.util.distanceTo
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEvent.*
import com.egoriku.grodnoroads.screen.map.domain.model.Alert
import com.egoriku.grodnoroads.screen.map.domain.model.Alert.CameraAlert
import com.egoriku.grodnoroads.screen.map.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.screen.map.domain.model.LocationState
import com.egoriku.grodnoroads.screen.settings.alerts.domain.component.AlertsComponent.AlertSettingsState
import com.google.android.gms.maps.model.LatLng

private const val MIN_DISTANCE = 20
private const val MIN_SPEED = 10

fun alertMessagesTransformation(): suspend (
    List<MapEvent>,
    LocationState,
    AlertSettingsState
) -> List<Alert> =
    { mapEvents: List<MapEvent>,
      locationState: LocationState,
      settingsState: AlertSettingsState ->
        when {
            locationState.speed > MIN_SPEED -> makeAlertMessage(
                mapEvents = mapEvents,
                locationState = locationState,
                distanceRadius = settingsState.alertDistance.distance
            )
            else -> emptyList()
        }
    }

private fun makeAlertMessage(
    mapEvents: List<MapEvent>,
    locationState: LocationState,
    distanceRadius: Int
) = mapEvents.mapNotNull { event ->
    val distance = computeDistance(
        eventLatLng = event.position,
        offsetLatLng = computeOffset(
            from = locationState.latLng,
            distance = distanceRadius.toDouble(),
            heading = locationState.bearing.toDouble()
        ),
        currentLatLnt = locationState.latLng,
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