package com.egoriku.grodnoroads.maps.compose

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import com.egoriku.grodnoroads.maps.compose.decorator.MapPaddingDecorator
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

@LayoutScopeMarker
@Immutable
interface MapUpdater {
    val paddingDecorator: MapPaddingDecorator

    val mapView: MapView

    fun addMarker(
        position: LatLng,
        icon: BitmapDescriptor? = null,
        onClick: () -> Unit = { },
        zIndex: Float = 0.0f,
        anchor: Offset = Offset(0.5f, 1.0f),
        rotation: Float = 0.0f,
        tag: Any? = null,
        visible: Boolean = true,
    )

    fun updateMarker(
        tag: Any?,
        position: LatLng
    )

    fun getMarker(tag: Any?): Marker?

    fun removeMarker(position: LatLng)
    fun hideMarker(tag: Any?)

    fun zoomIn()
    fun zoomOut()

    fun animateCamera(target: LatLng, bearing: Float, zoom: Float)
    fun animateZoom(zoom: Float)
}