package com.egoriku.grodnoroads.maps.compose

import android.annotation.SuppressLint
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import android.view.MotionEvent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.egoriku.grodnoroads.maps.compose.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.impl.MapUpdaterImpl
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraMoveStartedListener.*
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.buildGoogleMapOptions

internal val NoPadding = PaddingValues()

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun GoogleMap(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = NoPadding,
    mapProperties: MapProperties = DefaultMapProperties,
    mapUiSettings: MapUiSettings = DefaultMapUiSettings,
    onMapLoaded: (GoogleMap) -> Unit = {},
    cameraPositionProvider: () -> CameraPosition,
    onMapUpdaterChanged: (MapUpdater?) -> Unit = {},
    onProjectionChanged: (Projection) -> Unit = {},
    onZoomChanged: (Float) -> Unit = {},
    cameraMoveStateChanged: (CameraMoveState) -> Unit = {},
) {
    val updatedOnMapLoaded by rememberUpdatedState(onMapLoaded)
    val updatedCameraPositionProvider by rememberUpdatedState(cameraPositionProvider)
    val updatedOnMapUpdaterChanged by rememberUpdatedState(onMapUpdaterChanged)
    val updatedOnProjectionChanged by rememberUpdatedState(onProjectionChanged)
    val updatedOnZoomChanged by rememberUpdatedState(onZoomChanged)
    val updatedCameraMoveState by rememberUpdatedState(cameraMoveStateChanged)

    val context = LocalContext.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val mapView = remember {
        MapView(
            /* context = */ context,
            /* options = */ buildGoogleMapOptions {
                backgroundColor(backgroundColor.toArgb())
                camera(updatedCameraPositionProvider())
            }
        )
    }
    var googleMap by remember { mutableStateOf<GoogleMap?>(null) }
    var mapUpdater by remember(googleMap) {
        mutableStateOf(googleMap?.let { MapUpdaterImpl(it) })
    }

    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        updatedCameraMoveState(CameraMoveState.UserGesture)
                    }
                }
                false
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
        @Suppress("NAME_SHADOWING")
        val mapUpdater = mapUpdater ?: return@LaunchedEffect
        mapUpdater.updateContentPadding(
            contentPadding = contentPadding,
            density = density,
            layoutDirection = layoutDirection,
        )
    }

    LaunchedEffect(mapProperties) {
        val map = googleMap ?: return@LaunchedEffect
        updateMapProperties(map, mapProperties)
    }

    LaunchedEffect(googleMap) {
        @Suppress("NAME_SHADOWING")
        val googleMap = googleMap ?: return@LaunchedEffect

        googleMap.setOnMapLoadedCallback { updatedOnMapLoaded(googleMap) }
        googleMap.setOnCameraMoveStartedListener { reason ->
            val state = when (reason) {
                REASON_GESTURE -> CameraMoveState.UserGesture
                REASON_API_ANIMATION, REASON_DEVELOPER_ANIMATION -> CameraMoveState.Animating
                else -> error("unsupported value")
            }
            updatedCameraMoveState(state)
        }
        googleMap.setOnCameraIdleListener {
            updatedCameraMoveState(CameraMoveState.Idle(zoomLevel = googleMap.cameraPosition.zoom))
            updatedOnProjectionChanged(googleMap.projection)
        }
        googleMap.setOnCameraMoveListener {
            val zoomLevel = googleMap.cameraPosition.zoom
            updatedOnZoomChanged(zoomLevel)
        }

        updateMapProperties(googleMap, mapProperties)
        updateMapUiSettings(googleMap, mapUiSettings)
    }

    MapLifecycle(mapView)
}

sealed interface CameraMoveState {
    data object UserGesture : CameraMoveState
    data object Animating : CameraMoveState

    @JvmInline
    value class Idle(val zoomLevel: Float) : CameraMoveState
}

private fun MapPaddingDecorator.updateContentPadding(
    contentPadding: PaddingValues,
    density: Density,
    layoutDirection: LayoutDirection
) {
    with(density) {
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
        mapType = mapProperties.mapType.value
        isTrafficEnabled = mapProperties.isTrafficEnabled
        setMapStyle(mapProperties.mapStyleOptions)
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
    val previousState = remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
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
                //handled in onDispose
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
