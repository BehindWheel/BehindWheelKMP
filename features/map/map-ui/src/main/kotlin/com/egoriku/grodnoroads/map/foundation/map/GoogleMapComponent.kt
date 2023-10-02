package com.egoriku.grodnoroads.map.foundation.map

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.foundation.map.configuration.calculateCameraPositionValues
import com.egoriku.grodnoroads.map.foundation.map.debug.DebugView
import com.egoriku.grodnoroads.shared.appcomponent.FeatureFlags.MAP_DEBUG_OVERLAY_ENABLED
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun GoogleMapComponent(
    cameraPositionState: CameraPositionState,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    appMode: AppMode,
    mapConfig: MapConfig,
    lastLocation: LastLocation,
    onMapLoaded: () -> Unit,
    loading: @Composable BoxScope.() -> Unit,
    onPositionChanged: (LatLng) -> Unit = {},
    onCameraMoving: (Boolean) -> Unit,
    onProjection: (Projection?) -> Unit,
    mapZoomChangeEnabled: Boolean,
    onMapZoom: (Float) -> Unit,
    locationChangeEnabled: Boolean,
    animateCamera: Boolean,
    onLocation: (LatLng) -> Unit,
    onCameraChanges: (Boolean) -> Unit,
    content: (@Composable @GoogleMapComposable () -> Unit),
) {
    logD("animate = $animateCamera")
    if (mapConfig == MapConfig.EMPTY) return
    var isMapLoaded by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp

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

    val chooseLocationState = rememberMarkerState(position = lastLocation.latLng.value)
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

    LaunchedEffect(animateCamera, appMode) {
        when (appMode) {
            AppMode.Default, AppMode.ChooseLocation -> {
                onCameraChanges(true)
            }
            AppMode.Drive -> onCameraChanges(animateCamera)
        }
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


    LaunchedEffect(lastLocation, cameraPositionValues) {
        if (!animateCamera) return@LaunchedEffect
        if (!isMapLoaded) return@LaunchedEffect

        if (lastLocation == LastLocation.None) return@LaunchedEffect

        if (appMode == AppMode.Drive) {

        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            contentPadding = paddingValues
        ) {
            content()
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
}