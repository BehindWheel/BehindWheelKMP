package com.egoriku.grodnoroads.maps.compose.updater

import android.graphics.Point
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.toLatLng
import com.egoriku.grodnoroads.maps.compose.core.Projection
import com.egoriku.grodnoroads.maps.compose.extension.zoom
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecoratorImpl
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.mapLongClickEvents
import com.google.maps.android.ktx.markerClickEvents
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.model.markerOptions
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class MapUpdaterAndroid(
    private val mapView: MapView,
    private val googleMap: GoogleMap,
    override val paddingDecorator: MapPaddingDecorator = MapPaddingDecoratorImpl(googleMap),
    private val onZoomChanged: () -> Unit
) : MapUpdaterBase(),
    MapPaddingDecorator by paddingDecorator {

    private val bottomRightPoint by lazy {
        val projection = projection
        projection.toScreenLocation(projection.visibleRegion.nearRight)
    }
    private val center: Point by lazy { Point(bottomRightPoint.x / 2, bottomRightPoint.y / 2) }
    private val offset: Point by lazy { Point(center.x, 2 * bottomRightPoint.y / 3) }

    override val currentZoom: Float
        get() = googleMap.zoom

    override val projection: Projection
        get() = googleMap.projection

    override fun attach() {
        googleMap.markerClickEvents()
            .onEach { emitClickedMarker(it) }
            .launchIn(scope)

        googleMap.mapLongClickEvents()
            .onEach { emitMapLongClickEvent(it.toLatLng()) }
            .launchIn(scope)
    }

    override fun addMarker(markerOptions: MarkerOptions): Marker? {
        return googleMap.addMarker(
            markerOptions {
                title(markerOptions.title)
                position(markerOptions.position.platform)
                icon(markerOptions.icon)
                zIndex(markerOptions.zIndex)

                if (markerOptions.rotation != null) {
                    rotation(markerOptions.rotation)
                }
                if (markerOptions.anchor != null) {
                    anchor(markerOptions.anchor.u, markerOptions.anchor.v)
                }
            }
        )
    }

    override fun zoomIn() {
        onZoomChanged()
        if (!shouldZoomIn()) return
        googleMap.animateCamera(CameraUpdateFactory.zoomIn())
    }

    override fun zoomOut() {
        onZoomChanged()
        if (!shouldZoomOut()) return
        googleMap.animateCamera(CameraUpdateFactory.zoomOut())
    }

    override fun animateCurrentLocation(target: LatLng, zoom: Float, bearing: Float) {
        additionalPadding(top = mapView.height / 3)
        animateCamera(
            cameraUpdate = CameraUpdateFactory.newCameraPosition(
                cameraPosition {
                    target(target.platform)
                    bearing(bearing)
                    zoom(zoom)
                    tilt(NAVIGATION_CAMERA_TILT.toFloat())
                }
            ),
            duration = 700,
            onFinish = { additionalPadding(top = 0) },
            onCancel = { additionalPadding(top = 0) }
        )
    }

    override fun animateCamera(target: LatLng, zoom: Float, bearing: Float) {
        animateWithShadowPoint(
            target = target,
            zoom = zoom,
            bearing = bearing.toDouble()
        )
        updateLastLocationAndZoom(target, zoom)
    }

    override fun Projection.getCenterLocation(): LatLng = fromScreenLocation(center).toLatLng()

    override fun Projection.getOffsetLocation(): LatLng = fromScreenLocation(offset).toLatLng()

    override fun animateShadowCamera(shadowTarget: LatLng, bearing: Double, zoom: Float) {
        animateCamera(
            cameraUpdate = CameraUpdateFactory.newCameraPosition(
                cameraPosition {
                    target(shadowTarget.platform)
                    bearing(bearing.toFloat())
                    zoom(zoom)
                    tilt(NAVIGATION_CAMERA_TILT.toFloat())
                }
            ),
            duration = SHADOW_CAMERA_ANIMATION_DURATION_MS
        )
    }

    override fun animateTarget(
        target: LatLng,
        zoom: Float?,
        bearing: Float,
        onFinish: () -> Unit,
        onCancel: () -> Unit
    ) {
        val zoomLevel = zoom ?: googleMap.zoom
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(
            cameraPosition {
                target(target.platform)
                bearing(bearing)
                zoom(zoomLevel)
                tilt(0.0f)
            }
        )
        animateCamera(
            cameraUpdate = cameraUpdate,
            duration = 1000,
            onFinish = onFinish,
            onCancel = onCancel
        )
    }

    override fun animateZoom(zoom: Float) {
        val target = googleMap.cameraPosition.target
        val bearing = googleMap.cameraPosition.bearing
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(
            cameraPosition {
                target(target)
                bearing(bearing)
                zoom(zoom)
                tilt(0.0f)
            }
        )
        animateCamera(cameraUpdate = cameraUpdate, duration = 1000)
    }

    override fun rotateToNorth() {
        val currentBearing = googleMap.cameraPosition.bearing
        if (currentBearing != 0f) {
            val target = googleMap.cameraPosition.target
            val zoom = googleMap.cameraPosition.zoom
            val tilt = googleMap.cameraPosition.tilt
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(
                cameraPosition {
                    target(target)
                    bearing(0f)
                    zoom(zoom)
                    tilt(tilt)
                }
            )
            animateCamera(cameraUpdate = cameraUpdate, duration = 1000)
        }
    }

    private fun animateCamera(
        cameraUpdate: CameraUpdate,
        duration: Int,
        onFinish: () -> Unit = {},
        onCancel: () -> Unit = {}
    ) = googleMap.animateCamera(
        cameraUpdate,
        duration,
        object : GoogleMap.CancelableCallback {
            override fun onCancel() = onCancel()

            override fun onFinish() = onFinish()
        }
    )
}
