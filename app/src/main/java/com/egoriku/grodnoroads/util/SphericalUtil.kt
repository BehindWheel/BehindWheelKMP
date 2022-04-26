package com.egoriku.grodnoroads.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object SphericalUtil {

    fun computeHeading(latLng: LatLng, latLng2: LatLng): Double {
        val radians = Math.toRadians(latLng.latitude)
        val radians2 = Math.toRadians(latLng.longitude)
        val radians3 = Math.toRadians(latLng2.latitude)
        val radians4 = Math.toRadians(latLng2.longitude) - radians2
        return wrap(
            Math.toDegrees(
                atan2(
                    sin(radians4) * cos(radians3),
                    cos(radians) * sin(radians3) - sin(radians) * cos(radians3) * cos(radians4)
                )
            ), -180.0, 180.0
        )
    }

    private fun wrap(d: Double, d2: Double, d3: Double): Double {
        return if (d < d2 || d >= d3) mod(
            d - d2,
            d3 - d2
        ) + d2 else d
    }

    private fun mod(x: Double, m: Double): Double {
        return (x % m + m) % m
    }

}