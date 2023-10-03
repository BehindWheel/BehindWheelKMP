package com.egoriku.grodnoroads.maps.compose.impl

import androidx.compose.ui.geometry.Offset
import com.egoriku.grodnoroads.extensions.consume
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecoratorImpl
import com.egoriku.grodnoroads.maps.compose.impl.model.InternalMarker
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.ktx.addMarker

internal class MapUpdaterImpl(
    private val googleMap: GoogleMap,
    override val mapView: MapView,
    override val paddingDecorator: MapPaddingDecorator = MapPaddingDecoratorImpl(googleMap)
) : MapUpdater,
    MapStateUpdater,
    MapPaddingDecorator by paddingDecorator {

    private val markers = mutableListOf<InternalMarker>()
    private var minZoom = -1f
    private var maxZoom = -1f

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
        visible: Boolean,
    ) {
        val internalMarker = markers.find { it.marker.position == position }
        if (internalMarker == null) {
            val marker = googleMap.addMarker {
                position(position)
                icon(icon)
                zIndex(zIndex)
                anchor(anchor.x, anchor.y)
                rotation(rotation)
                visible(visible)
            }
            if (marker != null) {
                // logD("add marker")
                marker.tag = tag
                markers.add(InternalMarker(marker, onClick))
            }
        } else {
            logD("update marker")
            internalMarker.marker.apply {
                this.position = position
                this.zIndex = zIndex
                setIcon(icon)
                setAnchor(anchor.x, anchor.y)
                setRotation(rotation)
                isVisible = visible
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

    override fun hideMarker(tag: Any?) {
        logD("removeMarker: tag=$tag")
        markers
            .find { it.marker.tag == tag }
            ?.run { marker.isVisible = false }
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

    override fun animateCamera(cameraUpdate: CameraUpdate, duration: Int) {
        googleMap.animateCamera(cameraUpdate, duration, null)
    }
}