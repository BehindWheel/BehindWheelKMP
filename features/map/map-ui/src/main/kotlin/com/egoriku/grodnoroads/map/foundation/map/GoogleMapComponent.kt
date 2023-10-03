package com.egoriku.grodnoroads.map.foundation.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.foundation.map.debug.DebugView
import com.egoriku.grodnoroads.shared.appcomponent.FeatureFlags.MAP_DEBUG_OVERLAY_ENABLED
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
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
    onPositionChanged: (LatLng) -> Unit = {},
    onCameraMoving: (Boolean) -> Unit,
    onProjection: (Projection?) -> Unit,
    mapZoomChangeEnabled: Boolean,
    onMapZoom: (Float) -> Unit,
    locationChangeEnabled: Boolean,
    animateCamera: Boolean,
    onLocation: (LatLng) -> Unit,
    onCameraChanges: (Boolean) -> Unit,
    content: @Composable @GoogleMapComposable () -> Unit,
) {
    logD("animate = $animateCamera")
    if (mapConfig == MapConfig.EMPTY) return

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

    LaunchedEffect(lastLocation) {
        if (!animateCamera) return@LaunchedEffect

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