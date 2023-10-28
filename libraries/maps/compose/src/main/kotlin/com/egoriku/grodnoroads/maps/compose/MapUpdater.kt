package com.egoriku.grodnoroads.maps.compose

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import com.egoriku.grodnoroads.maps.compose.decorator.MapPaddingDecorator
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.SharedFlow

@LayoutScopeMarker
@Immutable
interface MapUpdater {
    val paddingDecorator: MapPaddingDecorator

    val clickedMarker: SharedFlow<Marker?>

    fun isInitialCameraAnimation(): Boolean
    fun resetLastLocation()

    fun addMarker(
        position: LatLng,
        icon: BitmapDescriptor? = null,
        zIndex: Float = 0.0f,
        anchor: Offset = Offset(0.5f, 1.0f),
        rotation: Float = 0.0f,
        title: String? = null
    ): Marker?

    fun addMarker(markerOptions: MarkerOptions): Marker?

    fun zoomIn()
    fun zoomOut()

    fun animateCamera(target: LatLng, zoom: Float, bearing: Float)
    fun animateZoom(zoom: Float)
}