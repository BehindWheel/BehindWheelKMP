package com.egoriku.grodnoroads.map.foundation.map

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEvent.*
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberCameraPositionValues
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberMapProperties
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberUiSettings
import com.egoriku.grodnoroads.map.markers.MobileCameraMarker
import com.egoriku.grodnoroads.map.markers.ReportsMarker
import com.egoriku.grodnoroads.map.markers.StationaryCameraMarker
import com.egoriku.grodnoroads.map.mode.drive.action.CloseAction
import com.egoriku.grodnoroads.map.util.MarkerCache
import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.collections.immutable.ImmutableList
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
    mapEvents: ImmutableList<MapEvent>,
    lastLocation: LastLocation,
    onMarkerClick: (Reports) -> Unit,
    isMapLoaded: MutableState<Boolean>,
    loading: @Composable BoxScope.() -> Unit
) {
    if (mapConfig == MapConfig.EMPTY) return

    var cameraPositionChangeEnabled by remember { mutableStateOf(true) }

    val markerCache = get<MarkerCache>()

    val cameraPositionState = rememberCameraPositionState()
    val cameraPositionValues = rememberCameraPositionValues(cameraPositionState, lastLocation)

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(lastLocation, cameraPositionValues) {
        if (!cameraPositionChangeEnabled) return@LaunchedEffect
        if (!isMapLoaded.value) return@LaunchedEffect

        if (lastLocation == LastLocation.None) return@LaunchedEffect

        if (appMode == AppMode.Drive) {
            if (mapConfig.zoomLevel == cameraPositionState.position.zoom) {
                cameraPositionState.animate(
                    update = buildCameraPosition(
                        target = cameraPositionValues.targetLatLngWithOffset,
                        bearing = cameraPositionValues.bearing,
                        zoomLevel = mapConfig.zoomLevel
                    ),
                    durationMs = 700
                )
            } else {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        /* latLng = */ cameraPositionValues.initialLatLng,
                        /* zoom = */ mapConfig.zoomLevel
                    ),
                    durationMs = 700
                )
            }
        } else {
            cameraPositionState.animate(
                buildCameraPosition(
                    target = cameraPositionValues.initialLatLng,
                    bearing = cameraPositionValues.bearing,
                    zoomLevel = mapConfig.zoomLevel,
                    tilt = 0.0f
                ),
                700
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .matchParentSize()
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
            properties = rememberMapProperties(lastLocation, mapConfig, appMode),
            uiSettings = rememberUiSettings(),
            contentPadding = WindowInsets.statusBars.asPaddingValues(),
            onMapLoaded = {
                cameraPositionState.move(
                    update = CameraUpdateFactory.newLatLngZoom(
                        /* latLng = */ lastLocation.latLng,
                        /* zoom = */ mapConfig.zoomLevel
                    )
                )
                isMapLoaded.value = true
            }
        ) {
            mapEvents.forEach { mapEvent ->
                when (mapEvent) {
                    is StationaryCamera -> StationaryCameraMarker(
                        stationaryCamera = mapEvent,
                        onFromCache = { markerCache.getVector(id = it) }
                    )

                    is Reports -> ReportsMarker(mapEvent, onMarkerClick)
                    is MobileCamera -> MobileCameraMarker(
                        mobileCamera = mapEvent,
                        onFromCache = { markerCache.getVector(id = it) })
                }
            }

            if (appMode != AppMode.Default && lastLocation != LastLocation.None) {
                Marker(
                    state = MarkerState(position = lastLocation.latLng),
                    icon = markerCache.getVector(id = R.drawable.ic_arrow),
                    rotation = cameraPositionValues.markerRotation,
                    anchor = Offset(0.5f, 0.5f),
                    zIndex = 1f
                )
            }
        }

        OverlayActions(
            modifier = Modifier.padding(end = 16.dp),
            zoomIn = {
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.zoomIn(), 200)
                }
            }, zoomOut = {
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.zoomOut(), 200)
                }
            })

        loading()

        //DebugView(cameraPositionState = cameraPositionState)
    }
}

@Composable
private fun BoxScope.OverlayActions(
    modifier: Modifier = Modifier,
    zoomIn: () -> Unit,
    zoomOut: () -> Unit
) {
    Column(modifier = modifier.align(Alignment.CenterEnd)) {
        CloseAction(imageVector = Icons.Default.Add, onClick = zoomIn)
        CloseAction(imageVector = Icons.Default.Remove, onClick = zoomOut)
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