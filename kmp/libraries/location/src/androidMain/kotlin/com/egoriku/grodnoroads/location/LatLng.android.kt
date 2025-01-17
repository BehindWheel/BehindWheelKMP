package com.egoriku.grodnoroads.location

actual typealias PlatformLatLng = com.google.android.gms.maps.model.LatLng

fun PlatformLatLng.toLatLng() = LatLng(this)

actual data class LatLng actual constructor(val platform: PlatformLatLng) {
    actual val latitude: Double
        get() = platform.latitude

    actual val longitude: Double
        get() = platform.longitude

    actual constructor(latitude: Double, longitude: Double) : this(
        PlatformLatLng(latitude, longitude)
    )
}
