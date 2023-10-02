package com.egoriku.grodnoroads.maps.compose

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
import com.egoriku.grodnoroads.maps.compose.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.impl.MapUpdaterImpl
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
    onMapLoaded: () -> Unit,
    onInitialLocationTriggered: (GoogleMap) -> Unit,
    onMapUpdaterChanged: (MapUpdater?) -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val mapView = remember { MapView(context) }
    var map by remember { mutableStateOf<GoogleMap?>(null) }
    var mapUpdater by remember(map) {
        mutableStateOf(map?.let { MapUpdaterImpl(it, mapView) })
    }

    AndroidView(
        modifier = modifier,
        factory = { mapView },
        update = { logD("GoogleMap: update") }
    )

    DisposableEffect(mapUpdater) {
        mapUpdater?.run {
            attach()
            setMaxZoomPreference(mapProperties.maxZoomPreference)
            setMinZoomPreference(mapProperties.minZoomPreference)

            updateContentPadding(contentPadding, density, layoutDirection)
        }
        onMapUpdaterChanged(mapUpdater)
        onDispose {
            mapUpdater?.detach()
            mapUpdater = null
        }
    }

    LaunchedEffect(mapView) {
        map = mapView.awaitMap()
    }

    LaunchedEffect(contentPadding) {
        mapUpdater?.updateContentPadding(
            contentPadding = contentPadding,
            density = density,
            layoutDirection = layoutDirection,
        )
    }

    LaunchedEffect(mapProperties) {
        val map = map ?: return@LaunchedEffect

        updateMapProperties(map, mapProperties)
    }

    LaunchedEffect(map) {
        val googleMap = map ?: return@LaunchedEffect

        googleMap.setOnMapLoadedCallback(onMapLoaded)
        onInitialLocationTriggered(googleMap)

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
    with(density) {
        updateContentPadding(
            left = contentPadding.calculateLeftPadding(layoutDirection).roundToPx(),
            top = contentPadding.calculateTopPadding().roundToPx(),
            right = contentPadding.calculateRightPadding(layoutDirection).roundToPx(),
            bottom = contentPadding.calculateBottomPadding().roundToPx()
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
