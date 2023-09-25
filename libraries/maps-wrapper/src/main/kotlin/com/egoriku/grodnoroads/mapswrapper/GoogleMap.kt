package com.egoriku.grodnoroads.mapswrapper

import android.annotation.SuppressLint
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.mapswrapper.core.StableLatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.maps.android.ktx.awaitMap

internal val NoPadding = PaddingValues()

@Composable
fun GoogleMap(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = NoPadding,
    mapProperties: MapProperties = DefaultMapProperties,
    mapUiSettings: MapUiSettings = DefaultMapUiSettings,
    latLng: StableLatLng,
    zoomLevel: Float,
    onMapLoaded: () -> Unit,
    content: @Composable MapScope.() -> Unit = {},
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val mapView = remember { MapView(context) }
    var map by remember { mutableStateOf<GoogleMap?>(null) }
    val scope by remember(map) {
        mutableStateOf(map?.let { MapScopeInstance(it) })
    }

    AndroidView(
        modifier = modifier,
        factory = {
            mapView
        },
        update = {
           logD("GoogleMap: update")
        }
    )

    scope?.content()

    DisposableEffect(scope) {
        scope?.attach()
        onDispose {
            scope?.detach()
        }
    }

    LaunchedEffect(mapView) {
        map = mapView.awaitMap()
    }

    LaunchedEffect(contentPadding) {
        val map = map ?: return@LaunchedEffect

        updateContentPadding(
            contentPadding = contentPadding,
            map = map,
            density = density,
            layoutDirection = layoutDirection,
        )
    }

    LaunchedEffect(mapProperties) {
        val map = map ?: return@LaunchedEffect

        updateMapProperties(map, mapProperties)
    }

    LaunchedEffect(map) {
        logD("GoogleMap: $map")
        val googleMap = map ?: return@LaunchedEffect

        googleMap.setOnMapLoadedCallback(onMapLoaded)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng.value, zoomLevel))
        updateContentPadding(
            contentPadding = contentPadding,
            map = googleMap,
            density = density,
            layoutDirection = layoutDirection,
        )
        updateMapProperties(googleMap, mapProperties)
        updateMapUiSettings(googleMap, mapUiSettings)
    }

    MapLifecycle(mapView)
}

private fun updateContentPadding(
    contentPadding: PaddingValues,
    map: GoogleMap,
    density: Density,
    layoutDirection: LayoutDirection
) {
    with(density) {
        map.setPadding(
            contentPadding.calculateLeftPadding(layoutDirection).roundToPx(),
            contentPadding.calculateTopPadding().roundToPx(),
            contentPadding.calculateRightPadding(layoutDirection).roundToPx(),
            contentPadding.calculateBottomPadding().roundToPx()
        )
    }
}

@SuppressLint("MissingPermission")
private fun updateMapProperties(googleMap: GoogleMap, mapProperties: MapProperties) {
    with(mapProperties) {
        googleMap.isMyLocationEnabled = isMyLocationEnabled
        googleMap.mapType = mapType.value
        googleMap.setMapStyle(mapStyleOptions)
        googleMap.setMinZoomPreference(minZoomPreference)
        googleMap.setMaxZoomPreference(maxZoomPreference)
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
