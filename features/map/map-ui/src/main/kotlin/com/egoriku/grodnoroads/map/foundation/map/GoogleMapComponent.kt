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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.extension.reLaunch
import com.egoriku.grodnoroads.map.foundation.map.configuration.calculateCameraPositionValues
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberMapProperties
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberUiSettings
import com.egoriku.grodnoroads.map.mode.drive.action.CloseAction
import com.egoriku.grodnoroads.map.util.MarkerCache
import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
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
    onPositionChanged: (LatLng) -> Unit = {},
    onCameraMoving: (Boolean) -> Unit,
    onProjection: (Projection?) -> Unit,
    content: (@Composable @GoogleMapComposable () -> Unit),
) {
    if (mapConfig == MapConfig.EMPTY) return

    var isMapLoaded by remember { mutableStateOf(false) }

    var cameraPositionChangeCount by remember { mutableStateOf(0) }
    var cameraPositionJob by remember { mutableStateOf<Job?>(null) }

    val markerCache = get<MarkerCache>()

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val cameraPositionState = rememberCameraPositionState()

    val cameraPositionValues by remember(cameraPositionState, lastLocation) {
        derivedStateOf {
            val projection = cameraPositionState.projection

            calculateCameraPositionValues(
                screenHeight = screenHeight,
                projection = projection,
                lastLocation = lastLocation
            )
        }
    }

    val chooseLocationState = rememberMarkerState(position = lastLocation.latLng)
    var chosenLocation by remember { mutableStateOf(chooseLocationState.position) }

    if (chooseLocationState.dragState == DragState.END) {
        chosenLocation = chooseLocationState.position
    }

    LaunchedEffect(cameraPositionState.position) {
        onPositionChanged(cameraPositionState.position.target)
    }

    LaunchedEffect(cameraPositionState.isMoving) {
        onCameraMoving(cameraPositionState.isMoving)
    }

    LaunchedEffect(cameraPositionState.projection) {
        onProjection(cameraPositionState.projection)
    }

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
        if (lastLocation == LastLocation.None) return@LaunchedEffect

        if (appMode == AppMode.Default && isMapLoaded) {
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
        if (appMode == AppMode.ChooseLocation && isMapLoaded) {
            cameraPositionState.animate(
                buildCameraPosition(
                    target = cameraPositionState.position.target,
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
        }

        if (!isMapLoaded) {
            loading()
        }
    }

    if (isMapLoaded) {
        MapOverlayActions(
            cameraPositionState = cameraPositionState,
            onChanged = { cameraPositionChangeCount++ }
        )
    }
    // DebugView(cameraPositionState = cameraPositionState)
}

@Composable
fun MapOverlayActions(
    cameraPositionState: CameraPositionState,
    onChanged: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterEnd)
        ) {
            CloseAction(
                imageVector = Icons.Default.Add,
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.zoomIn(), 200)
                        onChanged()
                    }
                }
            )
            CloseAction(
                imageVector = Icons.Default.Remove,
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.zoomOut(), 200)
                        onChanged()
                    }
                }
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