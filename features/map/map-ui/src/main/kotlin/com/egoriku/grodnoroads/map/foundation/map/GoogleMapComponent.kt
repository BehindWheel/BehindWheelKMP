package com.egoriku.grodnoroads.map.foundation.map

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.ActionButton
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.extension.reLaunch
import com.egoriku.grodnoroads.map.foundation.map.configuration.calculateCameraPositionValues
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberMapProperties
import com.egoriku.grodnoroads.map.foundation.map.configuration.rememberUiSettings
import com.egoriku.grodnoroads.map.foundation.map.debug.DebugView
import com.egoriku.grodnoroads.map.util.MarkerCache
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.appcomponent.FeatureFlags.MAP_DEBUG_OVERLAY_ENABLED
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun GoogleMapComponent(
    paddingValues: PaddingValues,
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
    mapZoomChangeEnabled: Boolean,
    onMapZoom: (Float) -> Unit,
    locationChangeEnabled: Boolean,
    onLocation: (LatLng) -> Unit,
    content: (@Composable @GoogleMapComposable () -> Unit),
) {
    if (mapConfig == MapConfig.EMPTY) return

    var isMapLoaded by remember { mutableStateOf(false) }

    var cameraPositionChangeCount by remember { mutableStateOf(0) }
    var cameraPositionJob by remember { mutableStateOf<Job?>(null) }

    val markerCache = koinInject<MarkerCache>()

    val screenHeight = LocalConfiguration.current.screenHeightDp
    val cameraPositionState = rememberCameraPositionState()

    val cameraPositionValues by remember(cameraPositionState, lastLocation) {
        derivedStateOf {
            val projection = cameraPositionState.projection

            calculateCameraPositionValues(
                isMapLoaded = isMapLoaded,
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

    if (mapZoomChangeEnabled) {
        LaunchedEffect(cameraPositionState.position.zoom) {
            onMapZoom(cameraPositionState.position.zoom)
        }
    }

    if (locationChangeEnabled) {
        LaunchedEffect(cameraPositionState.position.target) {
            snapshotFlow { cameraPositionState.position.target }
                .distinctUntilChanged()
                .debounce(100)
                .collect(onLocation)
        }
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
                        awaitEachGesture {
                            awaitFirstDown(requireUnconsumed = false)
                            cameraPositionChangeCount++
                        }
                    }
                },
            cameraPositionState = cameraPositionState,
            properties = rememberMapProperties(lastLocation, mapConfig, appMode),
            uiSettings = rememberUiSettings(),
            onMapLoaded = {
                cameraPositionState.move(
                    update = CameraUpdateFactory.newLatLngZoom(
                        /* latLng = */ lastLocation.latLng,
                        /* zoom = */ mapConfig.zoomLevel
                    )
                )
                isMapLoaded = true
                onMapLoaded()
            },
            contentPadding = paddingValues
        ) {
            content()
            if (appMode == AppMode.Drive && lastLocation != LastLocation.None) {
                val isLight = MaterialTheme.colorScheme.isLight
                Marker(
                    state = MarkerState(position = lastLocation.latLng),
                    icon = markerCache.getVector(
                        id = if (isLight) R.drawable.ic_navigation_arrow_black else R.drawable.ic_navigation_arrow_white,
                    ),
                    rotation = cameraPositionValues.markerRotation,
                    anchor = Offset(0.5f, 0.5f),
                    zIndex = 1f
                )
            }
        }

        if (!isMapLoaded) {
            loading()
        }
        if (MAP_DEBUG_OVERLAY_ENABLED) {
            DebugView(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp),
                cameraPositionState = cameraPositionState
            )
        }
    }

    if (isMapLoaded) {
        MapOverlayActions(
            cameraPositionState = cameraPositionState,
            onChanged = { cameraPositionChangeCount++ }
        )
    }
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
            ActionButton(
                imageVector = Icons.Default.Add,
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.zoomIn(), 200)
                        onChanged()
                    }
                }
            )
            ActionButton(
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