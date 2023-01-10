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
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.extension.reLaunch
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberCameraPositionValues
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberMapProperties
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberUiSettings
import com.egoriku.grodnoroads.map.mode.drive.action.CloseAction
import com.egoriku.grodnoroads.map.util.MarkerCache
import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.*
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
    lastLocation: LastLocation,
    onMapLoaded: () -> Unit,
    containsOverlay: Boolean,
    loading: @Composable BoxScope.() -> Unit,
    content: (@Composable @GoogleMapComposable () -> Unit),
) {
    if (mapConfig == MapConfig.EMPTY) return
    var isMapLoaded by remember { mutableStateOf(false) }

    var cameraPositionChangeCount by remember { mutableStateOf(0) }
    var cameraPositionJob by remember { mutableStateOf<Job?>(null) }

    val markerCache = get<MarkerCache>()

    val cameraPositionState = rememberCameraPositionState()
    val cameraPositionValues = rememberCameraPositionValues(cameraPositionState, lastLocation)

    val chooseLocationState = rememberMarkerState(position = lastLocation.latLng)
    var chosenLocation by remember { mutableStateOf(chooseLocationState.position) }

    if (chooseLocationState.dragState == DragState.END) {
        chosenLocation = chooseLocationState.position
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(cameraPositionChangeCount, containsOverlay) {
        if (cameraPositionChangeCount != 0) {
            cameraPositionJob = reLaunch(cameraPositionJob) {
                delay(3000)
                cameraPositionChangeCount = 0
            }
        }
        if (containsOverlay) {
            cameraPositionJob?.cancel()
        }
    }

    LaunchedEffect(appMode) {
        if (appMode == AppMode.Default) {
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

    LaunchedEffect(lastLocation, cameraPositionValues) {
        if (cameraPositionChangeCount != 0) return@LaunchedEffect
        if (!isMapLoaded) return@LaunchedEffect

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
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                    coroutineScope {
                        forEachGesture {
                            awaitPointerEventScope {
                                awaitFirstDown(requireUnconsumed = false)

                                do {
                                    val event = awaitPointerEvent()
                                    cameraPositionChangeCount++

                                } while (event.changes.any { it.pressed })
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
                isMapLoaded = true
                onMapLoaded()
            }
        ) {
            content()
            if (appMode == AppMode.Drive && lastLocation != LastLocation.None) {
                Marker(
                    state = MarkerState(position = lastLocation.latLng),
                    icon = markerCache.getVector(id = R.drawable.ic_arrow),
                    rotation = cameraPositionValues.markerRotation,
                    anchor = Offset(0.5f, 0.5f),
                    zIndex = 1f
                )
            }

            val markerClick: (Marker) -> Boolean = {
                logD("${it.title} was clicked")
                cameraPositionState.projection?.let { projection ->
                    logD("The current projection is: $projection")
                }
                false
            }

            if (appMode == AppMode.ChooseLocation) {
                Marker(
                    state = chooseLocationState,
                    title = "Choose Location",
                    onClick = markerClick
                )
            }
        }

        OverlayActions(
            modifier = Modifier.padding(end = 16.dp),
            zoomIn = {
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.zoomIn(), 200)
                    cameraPositionChangeCount++
                }
            },
            zoomOut = {
                coroutineScope.launch {
                    cameraPositionState.animate(CameraUpdateFactory.zoomOut(), 200)
                    cameraPositionChangeCount++
                }
            }
        )

        if (!isMapLoaded) {
            loading()
        }

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