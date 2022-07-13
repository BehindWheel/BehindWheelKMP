package com.egoriku.grodnoroads.screen.map.ui

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.map.rememberCameraPositionValues
import com.egoriku.grodnoroads.foundation.map.rememberMapProperties
import com.egoriku.grodnoroads.foundation.map.rememberUiSettings
import com.egoriku.grodnoroads.screen.map.domain.AppMode
import com.egoriku.grodnoroads.screen.map.domain.GrodnoRoadsMapPreferences
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.egoriku.grodnoroads.screen.map.domain.MapEvent
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.*
import com.egoriku.grodnoroads.screen.map.ui.markers.MobileCameraMarker
import com.egoriku.grodnoroads.screen.map.ui.markers.ReportsMarker
import com.egoriku.grodnoroads.screen.map.ui.markers.StationaryCameraMarker
import com.egoriku.grodnoroads.util.MarkerCache
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

@Composable
fun GoogleMapView(
    modifier: Modifier,
    mapEvents: List<MapEvent>,
    mode: AppMode,
    mapPreferences: GrodnoRoadsMapPreferences,
    locationState: LocationState,
    onMarkerClick: (Reports) -> Unit
) {
    val markerCache = get<MarkerCache>()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locationState.latLng, 12.5f)
    }

    var cameraPositionChangeEnabled by remember { mutableStateOf(true) }

    val cameraPositionValues = rememberCameraPositionValues(cameraPositionState, locationState)
    val zoomLevel by remember { mutableStateOf(14f) }

    LaunchedEffect(locationState, cameraPositionValues) {
        if (!cameraPositionChangeEnabled) return@LaunchedEffect

        if (mode == AppMode.Drive) {
            if (locationState != LocationState.None && cameraPositionValues.targetLatLngWithOffset != LocationState.None.latLng) {
                if (zoomLevel == cameraPositionState.position.zoom) {
                    cameraPositionState.animate(
                        buildCameraPosition(
                            target = cameraPositionValues.targetLatLngWithOffset,
                            bearing = cameraPositionValues.bearing,
                            zoomLevel = zoomLevel
                        ),
                        700
                    )
                } else {
                    cameraPositionState.animate(
                        buildCameraPosition(
                            target = cameraPositionValues.initialLatLng,
                            bearing = cameraPositionValues.bearing,
                            zoomLevel = zoomLevel
                        ),
                        700
                    )
                }
            }
        } else {
            cameraPositionState.animate(
                buildCameraPosition(
                    target = cameraPositionValues.initialLatLng,
                    bearing = cameraPositionValues.bearing,
                    zoomLevel = 12.5f,
                    tilt = 0.0f
                ),
                700
            )
        }
    }

    GoogleMap(
        modifier = modifier
            .pointerInput(Unit) {
                coroutineScope {
                    var cameraPositionJob: Job? = null

                    forEachGesture {
                        awaitPointerEventScope {
                            awaitFirstDown(requireUnconsumed = false)

                            do {
                                val event = awaitPointerEvent()
                                cameraPositionChangeEnabled = false

                            } while (event.changes.any { it.pressed })

                            cameraPositionJob?.cancel()
                            cameraPositionJob = launch {
                                delay(3000)
                                cameraPositionChangeEnabled = true
                            }
                        }
                    }
                }
            },
        cameraPositionState = cameraPositionState,
        properties = rememberMapProperties(
            locationState = locationState,
            mapPreferences = mapPreferences
        ),
        uiSettings = rememberUiSettings(),
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        mapEvents.forEach { mapEvent ->
            when (mapEvent) {
                is StationaryCamera -> StationaryCameraMarker(mapEvent, markerCache)
                is Reports -> ReportsMarker(mapEvent, onMarkerClick)
                is MobileCamera -> MobileCameraMarker(mapEvent, markerCache)
            }
        }

        if (locationState != LocationState.None && mode != AppMode.Map) {
            Marker(
                state = MarkerState(position = locationState.latLng),
                icon = markerCache.getVector(id = R.drawable.ic_arrow),
                rotation = cameraPositionValues.markerRotation,
                anchor = Offset(0.5f, 0.5f),
                zIndex = 1f
            )
        }
    }
}

private fun buildCameraPosition(
    target: LatLng,
    bearing: Float,
    zoomLevel: Float,
    tilt: Float = 25.0f
) = CameraUpdateFactory.newCameraPosition(
    CameraPosition.builder()
        .target(target)
        .bearing(bearing)
        .zoom(zoomLevel)
        .tilt(tilt)
        .build()
)