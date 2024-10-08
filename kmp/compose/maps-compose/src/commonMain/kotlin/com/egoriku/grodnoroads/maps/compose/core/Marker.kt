package com.egoriku.grodnoroads.maps.compose.core

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage

expect class Marker

expect fun Marker.remove()

expect fun Marker.updatePosition(latLng: LatLng)
expect fun Marker.setIcon(markerImage: MarkerImage)
expect val Marker.currentPosition: LatLng
