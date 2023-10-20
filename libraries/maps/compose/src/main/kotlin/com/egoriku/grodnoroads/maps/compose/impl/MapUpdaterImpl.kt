package com.egoriku.grodnoroads.maps.compose.impl

import android.graphics.Point
import androidx.compose.ui.geometry.Offset
import com.egoriku.grodnoroads.extensions.consume
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecoratorImpl
import com.egoriku.grodnoroads.maps.compose.impl.model.InternalMarker
import com.egoriku.grodnoroads.maps.core.extension.computeOffset
import com.egoriku.grodnoroads.maps.core.extension.distanceTo
import com.egoriku.grodnoroads.maps.core.extension.headingTo
import com.egoriku.grodnoroads.maps.core.extension.roundDistanceTo
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.model.cameraPosition

internal class MapUpdaterImpl(
    private val googleMap: GoogleMap,
    override val paddingDecorator: MapPaddingDecorator = MapPaddingDecoratorImpl(googleMap)
) : MapUpdater,
    MapStateUpdater,
    MapPaddingDecorator by paddingDecorator {

    private val markers = mutableListOf<InternalMarker>()
    private var minZoom = -1f
    private var maxZoom = -1f

    private val bottomRightPoint by lazy {
        val projection = googleMap.projection
        projection.toScreenLocation(projection.visibleRegion.nearRight)
    }
    private val center: Point by lazy { Point(bottomRightPoint.x / 2, bottomRightPoint.y / 2) }
    private val offset: Point by lazy { Point(center.x, 2 * bottomRightPoint.y / 3) }
    override var lastLocation: LatLng? = null

    private val currentZoom: Float
        get() = googleMap.cameraPosition.zoom

    override fun attach() {
        logD("attach")
        googleMap.setOnMarkerClickListener { marker ->
            when (val internalMarker = markers.find { it.marker == marker }) {
                null -> false
                else -> consume {
                    internalMarker.onClick()
                }
            }
        }
    }

    override fun setMaxZoomPreference(value: Float) {
        maxZoom = value
    }

    override fun setMinZoomPreference(value: Float) {
        minZoom = value
    }

    override fun detach() {
        logD("detach")
        googleMap.setOnMarkerClickListener(null)
        markers.clear()
    }

    override fun addMarker(
        position: LatLng,
        icon: BitmapDescriptor?,
        onClick: () -> Unit,
        zIndex: Float,
        anchor: Offset,
        rotation: Float,
        tag: Any?,
        title: String?
    ) {
        val internalMarker = markers.find { it.marker.position == position }
        if (internalMarker == null) {
            val marker = googleMap.addMarker {
                title(title)
                position(position)
                icon(icon)
                zIndex(zIndex)
                anchor(anchor.x, anchor.y)
                rotation(rotation)
            }
            if (marker != null) {
                marker.tag = tag
                markers.add(InternalMarker(marker, onClick))
            }
        } else {
            logD("update marker")
            internalMarker.marker.apply {
                this.position = position
                this.zIndex = zIndex
                setTitle(title)
                setIcon(icon)
                setAnchor(anchor.x, anchor.y)
                setRotation(rotation)
            }
        }
    }

    override fun updateMarker(tag: Any?, position: LatLng) {
        logD("updateMarker: $tag, $position")
        val internalMarker = markers.find { it.marker.tag == tag }

        internalMarker?.marker?.apply {
            this.position = position
        }
    }

    override fun removeMarker(position: LatLng) {
        logD("removeMarker")
        val internalMarker = markers.find { it.marker.position == position } ?: return
        internalMarker.marker.remove()
        markers.remove(internalMarker)
    }

    override fun removeMarker(tag: Any) {
        logD("removeMarker: tag=$tag")
        val internalMarker = markers.find { it.marker.tag == tag } ?: return
        internalMarker.marker.remove()
        markers.remove(internalMarker)
    }

    override fun getMarker(tag: Any?): Marker? {
        return markers.find { it.marker.tag == tag }?.marker
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
        animateCamera(
            cameraUpdate = CameraUpdateFactory.newCameraPosition(
                cameraPosition {
                    target(target)
                    bearing(bearing)
                    zoom(zoom)
                    tilt(35.0f)
                }
            ),
            duration = 1000
        )
        lastLocation = target
    }

    override fun animateCamera(target: LatLng, zoom: Float) {
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
        this.lastLocation = target
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