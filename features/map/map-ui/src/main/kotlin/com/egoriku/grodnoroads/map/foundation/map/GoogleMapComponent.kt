package com.egoriku.grodnoroads.map.foundation.map

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import com.egoriku.grodnoroads.extensions.common.StableList
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LocationState
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEvent.*
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberCameraPositionValues
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberMapProperties
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberUiSettings
import com.egoriku.grodnoroads.map.markers.MobileCameraMarker
import com.egoriku.grodnoroads.map.markers.ReportsMarker
import com.egoriku.grodnoroads.map.markers.StationaryCameraMarker
import com.egoriku.grodnoroads.map.util.MarkerCache
import com.egoriku.grodnoroads.resources.R
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
fun GoogleMapComponent(
    modifier: Modifier = Modifier,
    appMode: AppMode,
    mapConfig: MapConfig,
    mapEvents: StableList<MapEvent>,
    locationState: LocationState,
    onMarkerClick: (Reports) -> Unit,
) {
    var isMapLoaded by remember { mutableStateOf(false) }

    val zoomLevel = mapConfig.zoomLevel

    val markerCache = get<MarkerCache>()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(locationState.latLng, 12.5f)
    }

    var cameraPositionChangeEnabled by remember { mutableStateOf(true) }

    val cameraPositionValues = rememberCameraPositionValues(cameraPositionState, locationState)

    LaunchedEffect(locationState, cameraPositionValues) {
        if (!cameraPositionChangeEnabled) return@LaunchedEffect
        if (!isMapLoaded) return@LaunchedEffect

        if (appMode == AppMode.Drive) {
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
            .fillMaxSize()
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
        properties = rememberMapProperties(locationState, mapConfig),
        uiSettings = rememberUiSettings(),
        contentPadding = WindowInsets.statusBars.asPaddingValues(),
        onMapLoaded = {
            isMapLoaded = true
        }
    ) {
        mapEvents.forEach { mapEvent ->
            when (mapEvent) {
                is StationaryCamera -> StationaryCameraMarker(mapEvent, markerCache)
                is Reports -> ReportsMarker(mapEvent, onMarkerClick)
                is MobileCamera -> MobileCameraMarker(mapEvent, markerCache)
            }
        }

        if (locationState != LocationState.None && appMode != AppMode.Default) {
            Marker(
                state = MarkerState(position = locationState.latLng),
                icon = markerCache.getVector(id = R.drawable.ic_arrow),
                rotation = cameraPositionValues.markerRotation,
                anchor = Offset(0.5f, 0.5f),
                zIndex = 1f
            )
        }
    }

    // DebugView(cameraPositionState = cameraPositionState)
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