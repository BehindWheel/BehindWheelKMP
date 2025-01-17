package com.egoriku.grodnoroads.maps.compose.core

import cocoapods.GoogleMaps.GMSMarker
import com.egoriku.grodnoroads.location.IOSLatLng
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual typealias Marker = GMSMarker

@OptIn(ExperimentalForeignApi::class)
actual fun Marker.remove() {
    icon = null
    map = null
}

@OptIn(ExperimentalForeignApi::class)
actual fun Marker.updatePosition(latLng: LatLng) {
    position = latLng.cValue
}

actual fun Marker.setIcon(markerImage: MarkerImage) {
    this.icon = markerImage
}

@OptIn(ExperimentalForeignApi::class)
actual val Marker.currentPosition: LatLng
    get() = LatLng(IOSLatLng(position))
