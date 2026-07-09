@file:Suppress("ktlint:standard:filename")

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.location.toLatLng
import com.egoriku.grodnoroads.maps.compose.api.CameraMoveState
import com.egoriku.grodnoroads.maps.compose.api.ZoomLevelState
import com.egoriku.grodnoroads.maps.compose.configuration.MapProperties
import com.egoriku.grodnoroads.maps.compose.configuration.MapUiSettings
import com.egoriku.grodnoroads.maps.compose.configuration.toIosColorScheme
import com.egoriku.grodnoroads.maps.compose.configuration.toiOSMapType
import com.egoriku.grodnoroads.maps.compose.core.CameraPosition
import com.egoriku.grodnoroads.maps.compose.core.Projection
import com.egoriku.grodnoroads.maps.compose.extension.GoogleMap
import com.egoriku.grodnoroads.maps.compose.extension.zoom
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdaterIos
import com.egoriku.grodnoroads.maps.compose.util.toUIColor
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGRectZero
import platform.CoreLocation.CLLocationCoordinate2D
import platform.UIKit.UIApplication
import platform.darwin.NSObject
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSCameraPosition
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSMapView
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSMapViewDelegateProtocol
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSMarker

@OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)
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
    cameraMoveStateChange: (CameraMoveState) -> Unit,
    onBearingChange: (Float) -> Unit
) {
    val updatedOnMapLoad by rememberUpdatedState(onMapLoad)
    val updatedCameraPositionProvider by rememberUpdatedState(cameraPositionProvider)
    val updatedOnMapUpdaterChange by rememberUpdatedState(onMapUpdaterChange)
    val updatedOnProjectionChange by rememberUpdatedState(onProjectionChange)
    val updatedOnZoomChange by rememberUpdatedState(onZoomChange)
    val updatedCameraMoveState by rememberUpdatedState(cameraMoveStateChange)
    val updatedOnBearingChange by rememberUpdatedState(onBearingChange)

    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    var isInitialLoading by rememberMutableState { false }

    val googleMap = remember {
        GMSMapView(
            frame = CGRectZero.readValue(),
            camera = updatedCameraPositionProvider().platformCameraPosition
        ).apply {
            this.backgroundColor = backgroundColor.toUIColor()
        }
    }

    var mapUpdater by remember(googleMap) {
        mutableStateOf<MapUpdaterIos?>(
            MapUpdaterIos(
                googleMap = googleMap,
                onZoomChanged = {
                    updatedCameraMoveState(CameraMoveState.UserGesture)
                }
            )
        )
    }

    val delegate = remember {
        object : NSObject(), GMSMapViewDelegateProtocol {
            override fun mapViewDidFinishTileRendering(mapView: GMSMapView) {
                if (!isInitialLoading) {
                    updatedOnMapLoad(googleMap)
                    isInitialLoading = true
                }
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
                idleAtCameraPosition: GMSCameraPosition
            ) {
                updatedCameraMoveState(CameraMoveState.Idle)
                updatedOnProjectionChange(googleMap.projection)
                updatedOnZoomChange(ZoomLevelState.Idle(zoom = googleMap.zoom))
                updatedOnBearingChange(idleAtCameraPosition.bearing.toFloat())
            }

            override fun mapView(
                mapView: GMSMapView,
                didTapMarker: GMSMarker
            ): Boolean {
                mapUpdater?.clickMarker(didTapMarker)

                return true
            }

            override fun mapView(
                mapView: GMSMapView,
                didLongPressAtCoordinate: CValue<CLLocationCoordinate2D>
            ) {
                mapUpdater?.mapLongPressEvent(didLongPressAtCoordinate.toLatLng())
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
        },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative,
            isNativeAccessibilityEnabled = true
        )
    )

    DisposableEffect(mapUpdater) {
        mapUpdater?.run {
            attach()
            setMaxZoomPreference(mapProperties.maxZoomPreference)
            setMinZoomPreference(mapProperties.minZoomPreference)

            updateContentPadding(contentPadding, density, layoutDirection)
        }
        updatedOnMapUpdaterChange(mapUpdater)
        onDispose {
            mapUpdater?.detach()
            mapUpdater = null
        }
    }

    LaunchedEffect(contentPadding) {
        mapUpdater?.updateContentPadding(
            contentPadding = contentPadding,
            density = density,
            layoutDirection = layoutDirection
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
        val bottom = contentPadding.calculateBottomPadding().value.toInt() - insetBottom

        updateContentPadding(
            left = contentPadding.calculateLeftPadding(layoutDirection).roundToPx(),
            top = bottom,
            right = contentPadding.calculateRightPadding(layoutDirection).roundToPx(),
            bottom = bottom
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun updateMapProperties(googleMap: GMSMapView, mapProperties: MapProperties) {
    googleMap.apply {
        myLocationEnabled = mapProperties.isMyLocationEnabled
        mapType = mapProperties.mapType.toiOSMapType()
        overrideUserInterfaceStyle = mapProperties.mapColor.toIosColorScheme()
        trafficEnabled = mapProperties.isTrafficEnabled
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
