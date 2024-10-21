package com.egoriku.grodnoroads.shared.geolocation

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.geolocation.util.toKilometersPerHour
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation

@OptIn(ExperimentalForeignApi::class)
internal fun CLLocation.toLocationInfo(): LocationInfo {
    return coordinate().useContents {
        LocationInfo(
            latLng = LatLng(latitude, longitude),
            bearing = course.toFloat(),
            speed = when {
                speed >= 0 -> speed.toKilometersPerHour()
                else -> 0
            }
        )
    }
}
