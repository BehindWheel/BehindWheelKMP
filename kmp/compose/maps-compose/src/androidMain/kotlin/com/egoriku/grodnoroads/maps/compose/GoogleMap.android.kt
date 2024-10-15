package com.egoriku.grodnoroads.maps.compose

import android.annotation.SuppressLint
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import android.view.MotionEvent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.maps.compose.api.CameraMoveState
import com.egoriku.grodnoroads.maps.compose.api.ZoomLevelState
import com.egoriku.grodnoroads.maps.compose.configuration.MapProperties
import com.egoriku.grodnoroads.maps.compose.configuration.MapUiSettings
import com.egoriku.grodnoroads.maps.compose.configuration.toAndroidColorScheme
import com.egoriku.grodnoroads.maps.compose.configuration.toAndroidMapType
import com.egoriku.grodnoroads.maps.compose.core.CameraPosition
import com.egoriku.grodnoroads.maps.compose.extension.zoom
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdaterAndroid
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.Projection
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.buildGoogleMapOptions

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun GoogleMap(
    cameraPositionProvider: () -> CameraPosition,
    modifier: Modifier,
    backgroundColor: Color,
    contentPadding: PaddingValues,
    mapProperties: MapProperties,
    mapUiSettings: MapUiSettings,
    onMapLoad: (GoogleMap) -> Unit,
    onMapUpdaterChange: (MapUpdater?) -> Unit,
    onProjectionChange: (Projection) -> Unit,
    onZoomChange: (ZoomLevelState) -> Unit,
    cameraMoveStateChange: (CameraMoveState) -> Unit
) {
    val updatedOnMapLoaded by rememberUpdatedState(onMapLoad)
    val updatedCameraPositionProvider by rememberUpdatedState(cameraPositionProvider)
    val updatedOnMapUpdaterChanged by rememberUpdatedState(onMapUpdaterChange)
    val updatedOnProjectionChanged by rememberUpdatedState(onProjectionChange)
    val updatedOnZoomChanged by rememberUpdatedState(onZoomChange)
    val updatedCameraMoveState by rememberUpdatedState(cameraMoveStateChange)

    val context = LocalContext.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val mapView = remember {
        MapView(
            /* context = */
            context,
            /* options = */
            buildGoogleMapOptions {
                backgroundColor(backgroundColor.toArgb())
                camera(updatedCameraPositionProvider().platformCameraPosition)
            }
        )
    }
    var googleMap by rememberMutableState<GoogleMap?> { null }
    var mapUpdater by remember(googleMap) {
        mutableStateOf(
            googleMap?.let {
                MapUpdaterAndroid(
                    mapView = mapView,
                    googleMap = it,
                    onZoomChanged = {
                        updatedCameraMoveState(CameraMoveState.UserGesture)
                    }
                )
            }
        )
    }

    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .motionEventSpy {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> updatedCameraMoveState(CameraMoveState.UserGesture)
                    MotionEvent.ACTION_UP -> updatedCameraMoveState(CameraMoveState.Idle)
                }
            },
        factory = { mapView }
    )

    DisposableEffect(mapUpdater) {
        mapUpdater?.run {
            attach()
            setMaxZoomPreference(mapProperties.maxZoomPreference)
            setMinZoomPreference(mapProperties.minZoomPreference)

            updateContentPadding(contentPadding, density, layoutDirection)
        }
        updatedOnMapUpdaterChanged(mapUpdater)
        onDispose {
            mapUpdater?.detach()
            mapUpdater = null
        }
    }

    LaunchedEffect(mapView) {
        googleMap = mapView.awaitMap()
    }

    LaunchedEffect(contentPadding) {
        val mapUpdater = mapUpdater ?: return@LaunchedEffect
        mapUpdater.updateContentPadding(
            contentPadding = contentPadding,
            density = density,
            layoutDirection = layoutDirection
        )
    }

    LaunchedEffect(mapProperties) {
        val map = googleMap ?: return@LaunchedEffect
        updateMapProperties(map, mapProperties)
    }

    LaunchedEffect(googleMap) {
        val googleMap = googleMap ?: return@LaunchedEffect

        googleMap.setOnMapLoadedCallback { updatedOnMapLoaded(googleMap) }
        googleMap.setOnCameraMoveStartedListener { reason ->
            val state = when (reason) {
                GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> CameraMoveState.UserGesture
                GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION, GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION -> CameraMoveState.Animating
                else -> error("unsupported value")
            }
            updatedCameraMoveState(state)
        }
        googleMap.setOnCameraIdleListener {
            updatedCameraMoveState(CameraMoveState.Idle)
            updatedOnProjectionChanged(googleMap.projection)
            updatedOnZoomChanged(ZoomLevelState.Idle(zoom = googleMap.zoom))
        }
        googleMap.setOnCameraMoveListener {
            updatedOnZoomChanged(ZoomLevelState.Moving(zoom = googleMap.zoom))
            updatedOnProjectionChanged(googleMap.projection)
        }

        updateMapProperties(googleMap, mapProperties)
        updateMapUiSettings(googleMap, mapUiSettings)
    }

    MapLifecycle(mapView)
}

private fun MapPaddingDecorator.updateContentPadding(
    contentPadding: PaddingValues,
    density: Density,
    layoutDirection: LayoutDirection
) {
    println("left=$contentPadding")
    with(density) {
        println("left=${contentPadding.calculateLeftPadding(layoutDirection).roundToPx()}")
        println("top=${contentPadding.calculateBottomPadding().roundToPx()}")
        println("right=${contentPadding.calculateRightPadding(layoutDirection).roundToPx()}")
        println("bottom=${contentPadding.calculateBottomPadding().roundToPx()}")
        updateContentPadding(
            left = contentPadding.calculateLeftPadding(layoutDirection).roundToPx(),
            // make top padding the same as bottom
            top = contentPadding.calculateBottomPadding().roundToPx(),
            right = contentPadding.calculateRightPadding(layoutDirection).roundToPx(),
            bottom = contentPadding.calculateBottomPadding().roundToPx()
        )
    }
}

@SuppressLint("MissingPermission")
private fun updateMapProperties(googleMap: GoogleMap, mapProperties: MapProperties) {
    googleMap.apply {
        isMyLocationEnabled = mapProperties.isMyLocationEnabled
        mapType = mapProperties.mapType.toAndroidMapType()
        mapColorScheme = mapProperties.mapColor.toAndroidColorScheme()
        isTrafficEnabled = mapProperties.isTrafficEnabled
        setMinZoomPreference(mapProperties.minZoomPreference)
        setMaxZoomPreference(mapProperties.maxZoomPreference)
    }
}

private fun updateMapUiSettings(googleMap: GoogleMap, mapUiSettings: MapUiSettings) {
    with(googleMap.uiSettings) {
        isCompassEnabled = mapUiSettings.compassEnabled
        isMyLocationButtonEnabled = mapUiSettings.myLocationButtonEnabled
    }
}

@Composable
private fun MapLifecycle(mapView: MapView) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val previousState = rememberMutableState { Lifecycle.Event.ON_CREATE }
    DisposableEffect(context, lifecycle, mapView) {
        val mapLifecycleObserver = mapView.lifecycleObserver(previousState)
        val callbacks = mapView.componentCallbacks()

        lifecycle.addObserver(mapLifecycleObserver)
        context.registerComponentCallbacks(callbacks)

        onDispose {
            lifecycle.removeObserver(mapLifecycleObserver)
            context.unregisterComponentCallbacks(callbacks)
        }
    }
    DisposableEffect(mapView) {
        onDispose {
            mapView.onDestroy()
            mapView.removeAllViews()
        }
    }
}

private fun MapView.lifecycleObserver(previousState: MutableState<Lifecycle.Event>): LifecycleEventObserver =
    LifecycleEventObserver { _, event ->
        event.targetState
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                // Skip calling mapView.onCreate if the lifecycle did not go through onDestroy - in
                // this case the GoogleMap composable also doesn't leave the composition. So,
                // recreating the map does not restore state properly which must be avoided.
                if (previousState.value != Lifecycle.Event.ON_STOP) {
                    this.onCreate(Bundle())
                }
            }

            Lifecycle.Event.ON_START -> this.onStart()
            Lifecycle.Event.ON_RESUME -> this.onResume()
            Lifecycle.Event.ON_PAUSE -> this.onPause()
            Lifecycle.Event.ON_STOP -> this.onStop()
            Lifecycle.Event.ON_DESTROY -> {
                // handled in onDispose
            }

            else -> throw IllegalStateException()
        }
        previousState.value = event
    }

private fun MapView.componentCallbacks(): ComponentCallbacks = object : ComponentCallbacks {
    override fun onConfigurationChanged(config: Configuration) {}

    override fun onLowMemory() {
        this@componentCallbacks.onLowMemory()
    }
}
