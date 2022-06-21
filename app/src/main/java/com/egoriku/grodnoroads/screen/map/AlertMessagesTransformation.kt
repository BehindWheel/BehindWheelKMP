package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.extension.distanceTo
import com.egoriku.grodnoroads.screen.map.domain.Alert
import com.egoriku.grodnoroads.screen.map.domain.Alert.CameraAlert
import com.egoriku.grodnoroads.screen.map.domain.Alert.IncidentAlert
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.egoriku.grodnoroads.screen.map.domain.MapEvent
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.*
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

private const val MIN_DISTANCE = 20
private const val MIN_SPEED = 10

fun alertMessagesTransformation(): suspend (
    List<MapEvent>,
    LocationState,
    SettingsState
) -> List<Alert> =
    { mapEvents: List<MapEvent>,
      locationState: LocationState,
      settingsState: SettingsState ->
        when {
            locationState.speed > MIN_SPEED -> makeAlertMessage(
                mapEvents = mapEvents,
                locationState = locationState,
                distanceRadius = settingsState.alertDistanceRadius
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
            locationState = locationState,
            distanceRadius = distanceRadius
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

private fun computeOffset(locationState: LocationState, distanceRadius: Int) =
    SphericalUtil.computeOffset(
        locationState.latLng,
        distanceRadius.toDouble(),
        locationState.bearing.toDouble()
    )
