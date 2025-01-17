package com.egoriku.grodnoroads.maps.compose.core

import cocoapods.GoogleMaps.GMSProjection
import com.egoriku.grodnoroads.location.LatLng
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGPoint
import platform.CoreGraphics.CGPointMake

@OptIn(ExperimentalForeignApi::class)
actual typealias Projection = GMSProjection

@OptIn(ExperimentalForeignApi::class)
actual fun Projection.toScreenLatLng(point: Point): LatLng {
    return coordinateForPoint(point.cValue).useContents {
        LatLng(latitude, longitude)
    }
}

actual typealias PlatformPoint = IOSPoint

@OptIn(ExperimentalForeignApi::class)
class IOSPoint(val cValue: CValue<CGPoint>)

@OptIn(ExperimentalForeignApi::class)
actual class Point actual constructor(private val platformPoint: PlatformPoint) {
    actual val x: Float
        get() = platformPoint.cValue.useContents { x.toFloat() }

    actual val y: Float
        get() = platformPoint.cValue.useContents { y.toFloat() }

    val cValue: CValue<CGPoint>
        get() = platformPoint.cValue

    actual constructor(x: Float, y: Float) : this(
        IOSPoint(
            cValue = CGPointMake(
                x = x.toDouble(),
                y = y.toDouble()
            )
        )
    )
}
