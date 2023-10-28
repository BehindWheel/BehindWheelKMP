package com.egoriku.grodnoroads.maps.compose.impl

import android.graphics.Point
import androidx.compose.ui.geometry.Offset
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.extension.zoom
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecoratorImpl
import com.egoriku.grodnoroads.maps.core.extension.computeOffset
import com.egoriku.grodnoroads.maps.core.extension.distanceTo
import com.egoriku.grodnoroads.maps.core.extension.headingTo
import com.egoriku.grodnoroads.maps.core.extension.roundDistanceTo
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.ktx.OnMarkerDragEvent
import com.google.maps.android.ktx.markerClickEvents
import com.google.maps.android.ktx.markerDragEvents
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ktx.model.markerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class MapUpdaterImpl(
    private val googleMap: GoogleMap,
    override val paddingDecorator: MapPaddingDecorator = MapPaddingDecoratorImpl(googleMap)
) : MapUpdater,
    MapStateUpdater,
    MapPaddingDecorator by paddingDecorator {

    private val scope = CoroutineScope(Dispatchers.Main)

    private var minZoom = -1f
    private var maxZoom = -1f

    private val bottomRightPoint by lazy {
        val projection = googleMap.projection
        projection.toScreenLocation(projection.visibleRegion.nearRight)
    }
    private val center: Point by lazy { Point(bottomRightPoint.x / 2, bottomRightPoint.y / 2) }
    private val offset: Point by lazy { Point(center.x, 2 * bottomRightPoint.y / 3) }

    private var lastLocation: LatLng? = null
    private var lastZoom: Float? = null

    private val currentZoom: Float
        get() = googleMap.zoom

    private val _clickedMarker = MutableSharedFlow<Marker?>(replay = 0)
    override val clickedMarker = _clickedMarker.asSharedFlow()

    private val _draggedMarker = MutableSharedFlow<OnMarkerDragEvent?>(replay = 0)
    override val draggedMarker = _draggedMarker.asSharedFlow()

    override fun attach() {
        googleMap.markerClickEvents()
            .onEach { _clickedMarker.emit(it) }
            .launchIn(scope)

        googleMap.markerDragEvents()
            .onEach { _draggedMarker.emit(it) }
            .launchIn(scope)
    }

    override fun setMaxZoomPreference(value: Float) {
        maxZoom = value
    }

    override fun setMinZoomPreference(value: Float) {
        minZoom = value
    }

    override fun isInitialCameraAnimation() = lastLocation == null
    override fun resetLastLocation() {
        lastLocation = null
    }

    override fun addMarker(
        position: LatLng,
        icon: BitmapDescriptor?,
        zIndex: Float,
        anchor: Offset,
        rotation: Float,
        title: String?,
    ): Marker? = addMarker(
        markerOptions = markerOptions {
            title(title)
            position(position)
            icon(icon)
            zIndex(zIndex)
            anchor(anchor.x, anchor.y)
            rotation(rotation)
        }
    )

    override fun addMarker(markerOptions: MarkerOptions): Marker? {
        return googleMap.addMarker(markerOptions)
    }

    override fun addPolygon(polygonOptions: PolygonOptions): Polygon {
        return googleMap.addPolygon(polygonOptions)
    }

    override fun detach() {
        googleMap.setOnMarkerClickListener(null)
        scope.cancel()
    }

    override fun zoomIn() {
        if (currentZoom >= maxZoom) return
        googleMap.animateCamera(CameraUpdateFactory.zoomIn())
    }

    override fun zoomOut() {
        if (currentZoom <= minZoom) return
        googleMap.animateCamera(CameraUpdateFactory.zoomOut())
    }

    override fun animateCamera(target: LatLng, zoom: Float, bearing: Float) {
        if (lastLocation == null || lastZoom != zoom || googleMap.zoom != lastZoom) {
            animateCamera(
                cameraUpdate = CameraUpdateFactory.newCameraPosition(
                    cameraPosition {
                        target(target)
                        bearing(bearing)
                        zoom(zoom)
                        tilt(35.0f)
                    }
                ),
                duration = 700
            )
        } else {
            animateWithShadowPoint(target = target, zoom = zoom)
        }
        lastZoom = zoom
        lastLocation = target
    }

    private fun animateWithShadowPoint(target: LatLng, zoom: Float) {
        val lastLocation = lastLocation ?: return
        val distance = lastLocation roundDistanceTo target
        if (distance < 10) return

        val bearing = lastLocation headingTo target

        val projection = googleMap.projection
        val centerLocation = projection.fromScreenLocation(center)
        val offsetLocation = projection.fromScreenLocation(offset)

        val offsetDistance = centerLocation distanceTo offsetLocation

        val shadowTarget = computeOffset(target, offsetDistance, bearing)
        animateCamera(
            cameraUpdate = CameraUpdateFactory.newCameraPosition(
                cameraPosition {
                    target(shadowTarget)
                    bearing(bearing.toFloat())
                    zoom(zoom)
                    tilt(35.0f)
                }
            ),
            duration = 400
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

    private fun animateCamera(cameraUpdate: CameraUpdate, duration: Int) =
        googleMap.animateCamera(cameraUpdate, duration, null)
}