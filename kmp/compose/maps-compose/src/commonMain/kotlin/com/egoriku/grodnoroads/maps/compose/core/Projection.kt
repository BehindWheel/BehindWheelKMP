package com.egoriku.grodnoroads.maps.compose.core

import com.egoriku.grodnoroads.location.LatLng

expect class Projection

expect fun Projection.toScreenLatLng(point: Point): LatLng

expect class PlatformPoint

expect class Point(platformPoint: PlatformPoint) {
    val x: Float
    val y: Float

    constructor(x: Float, y: Float)
}