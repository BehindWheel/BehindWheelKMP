package com.egoriku.grodnoroads.location

expect class PlatformLatLng

expect class LatLng(platform: PlatformLatLng) {

    val latitude: Double
    val longitude: Double

    constructor(latitude: Double, longitude: Double)
}

fun LatLng.validateOrNull(): LatLng? {
    val isValid =
        !latitude.isNaN() &&
            !latitude.isInfinite() &&
            !longitude.isNaN() &&
            !longitude.isInfinite()

    return if (isValid) this else null
}
