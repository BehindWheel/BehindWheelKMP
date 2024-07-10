package com.egoriku.grodnoroads.maps.compose.core

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage

actual typealias Marker = com.google.android.gms.maps.model.Marker

actual fun Marker.remove() = remove()

actual fun Marker.updatePosition(latLng: LatLng) {
    position = latLng.platform
}

actual fun Marker.setIcon(markerImage: MarkerImage) {
    this.setIcon(markerImage)
}

actual val Marker.currentPosition: LatLng
    get() = LatLng(position)
