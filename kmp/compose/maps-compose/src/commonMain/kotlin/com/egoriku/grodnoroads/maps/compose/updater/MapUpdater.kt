package com.egoriku.grodnoroads.maps.compose.updater

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.core.Marker
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import kotlinx.coroutines.flow.SharedFlow

@Stable
interface MapUpdater {
    val paddingDecorator: MapPaddingDecorator
    val clickedMarker: SharedFlow<Marker?>

    fun isInitialCameraAnimation(): Boolean
    fun resetLastLocation()

    fun addMarker(markerOptions: MarkerOptions): Marker?

    fun zoomIn()
    fun zoomOut()

    fun animateCurrentLocation(target: LatLng, zoom: Float, bearing: Float)
    fun animateCamera(target: LatLng, zoom: Float, bearing: Float)
    fun animateTarget(
        target: LatLng,
        zoom: Float? = null,
        onFinish: () -> Unit = {},
        onCancel: () -> Unit = {}
    )

    fun animateZoom(zoom: Float)
}
