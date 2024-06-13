package com.egoriku.grodnoroads.location

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake

actual typealias PlatformLatLng = IOSLatLng

fun CValue<CLLocationCoordinate2D>.toLatLng() = LatLng(IOSLatLng(this))

@OptIn(ExperimentalForeignApi::class)
class IOSLatLng(val cValue: CValue<CLLocationCoordinate2D>)

@OptIn(ExperimentalForeignApi::class)
actual class LatLng actual constructor(private val platform: PlatformLatLng) {
    actual val latitude: Double
        get() = platform.cValue.useContents { latitude }

    actual val longitude: Double
        get() = platform.cValue.useContents { longitude }

    val cValue: CValue<CLLocationCoordinate2D>
        get() = platform.cValue

    actual constructor(latitude: Double, longitude: Double) : this(
        PlatformLatLng(
            cValue = CLLocationCoordinate2DMake(
                latitude = latitude,
                longitude = longitude
            )
        )
    )
}