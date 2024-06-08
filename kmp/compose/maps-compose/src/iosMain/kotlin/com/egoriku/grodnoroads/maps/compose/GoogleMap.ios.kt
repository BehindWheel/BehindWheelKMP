package com.egoriku.grodnoroads.maps.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.GMSMarker
import com.egoriku.grodnoroads.maps.compose.api.CameraMoveState
import com.egoriku.grodnoroads.maps.compose.api.ZoomLevelState
import com.egoriku.grodnoroads.maps.compose.configuration.MapProperties
import com.egoriku.grodnoroads.maps.compose.configuration.MapUiSettings
import com.egoriku.grodnoroads.maps.compose.configuration.toiOSMapType
import com.egoriku.grodnoroads.maps.compose.core.CameraPosition
import com.egoriku.grodnoroads.maps.compose.core.Projection
import com.egoriku.grodnoroads.maps.compose.extension.GoogleMap
import com.egoriku.grodnoroads.maps.compose.extension.zoom
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdaterImplIos
import com.egoriku.grodnoroads.maps.compose.util.toUIColor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectZero
import platform.UIKit.UIApplication
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun GoogleMap(
    modifier: Modifier,
    backgroundColor: Color,
    contentPadding: PaddingValues,
    mapProperties: MapProperties,
    mapUiSettings: MapUiSettings,
    onMapLoaded: (GoogleMap) -> Unit,
    cameraPositionProvider: () -> CameraPosition,
    onMapUpdaterChanged: (MapUpdater?) -> Unit,
    onProjectionChanged: (Projection) -> Unit,
    onZoomChanged: (ZoomLevelState) -> Unit,
    cameraMoveStateChanged: (CameraMoveState) -> Unit
) {
    val updatedOnMapLoaded by rememberUpdatedState(onMapLoaded)
    val updatedCameraPositionProvider by rememberUpdatedState(cameraPositionProvider)
    val updatedOnMapUpdaterChanged by rememberUpdatedState(onMapUpdaterChanged)
    val updatedOnProjectionChanged by rememberUpdatedState(onProjectionChanged)
    val updatedOnZoomChanged by rememberUpdatedState(onZoomChanged)
    val updatedCameraMoveState by rememberUpdatedState(cameraMoveStateChanged)

    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val googleMap = remember {
        GMSMapView(
            frame = CGRectZero.readValue(),
            camera = updatedCameraPositionProvider().platformCameraPosition
        ).apply {
            this.backgroundColor = backgroundColor.toUIColor()
        }
    }

    var mapUpdater by remember(googleMap) {
        mutableStateOf<MapUpdaterImplIos?>(
            MapUpdaterImplIos(
                googleMap = googleMap,
                onZoomChanged = {
                    updatedCameraMoveState(CameraMoveState.UserGesture)
                }
            )
        )
    }

    val delegate = remember {
        object : NSObject(), GMSMapViewDelegateProtocol {
            override fun mapViewSnapshotReady(mapView: GMSMapView) {
                updatedOnMapLoaded(googleMap)
            }

            override fun mapView(mapView: GMSMapView, willMove: Boolean) {
                val state = when {
                    willMove -> CameraMoveState.UserGesture
                    else -> CameraMoveState.Animating
                }
                updatedCameraMoveState(state)
            }

            override fun mapView(
                mapView: GMSMapView,
                @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
                idleAtCameraPosition: GMSCameraPosition
            ) {
                updatedCameraMoveState(CameraMoveState.Idle)
                updatedOnProjectionChanged(googleMap.projection)
                updatedOnZoomChanged(ZoomLevelState.Idle(zoom = googleMap.zoom))
            }

            override fun mapView(
                mapView: GMSMapView,
                @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
                didTapMarker: GMSMarker
            ): Boolean {
                mapUpdater?.clickMarker(didTapMarker)

                return true
            }
        }
    }

    UIKitView(
        factory = {
            googleMap.apply {
                setDelegate(delegate)
            }
        },
        modifier = Modifier.fillMaxSize(),
        onRelease = {
            it.removeFromSuperview()
        }
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

    LaunchedEffect(contentPadding) {
        mapUpdater?.updateContentPadding(
            contentPadding = contentPadding,
            density = density,
            layoutDirection = layoutDirection,
        )
    }

    LaunchedEffect(mapProperties) {
        updateMapProperties(googleMap, mapProperties)
    }

    LaunchedEffect(googleMap) {
        updateMapUiSettings(googleMap, mapUiSettings)
    }
}

private fun MapPaddingDecorator.updateContentPadding(
    contentPadding: PaddingValues,
    density: Density,
    layoutDirection: LayoutDirection
) {
    with(density) {
        updateContentPadding(
            left = contentPadding.calculateLeftPadding(layoutDirection).roundToPx(),
            top = contentPadding.calculateBottomPadding().roundToPx(),
            right = contentPadding.calculateRightPadding(layoutDirection).roundToPx(),
            bottom = contentPadding.calculateBottomPadding().value.toInt() - insetBottom
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun updateMapProperties(googleMap: GMSMapView, mapProperties: MapProperties) {
    googleMap.apply {
        myLocationEnabled = false
        mapType = mapProperties.mapType.toiOSMapType()
        trafficEnabled = mapProperties.isTrafficEnabled
        mapStyle = mapProperties.mapStyleOptions
        setMinZoom(mapProperties.minZoomPreference, mapProperties.maxZoomPreference)
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun updateMapUiSettings(googleMap: GMSMapView, mapUiSettings: MapUiSettings) {
    with(googleMap.settings) {
        compassButton = mapUiSettings.compassEnabled
        myLocationButton = mapUiSettings.myLocationButtonEnabled
    }
}

private val insetBottom = UIApplication.sharedApplication
    .keyWindow
    ?.safeAreaInsets
    ?.useContents { bottom }
    ?.toInt() ?: 0
