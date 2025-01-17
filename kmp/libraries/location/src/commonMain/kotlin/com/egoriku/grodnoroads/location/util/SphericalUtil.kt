package com.egoriku.grodnoroads.location.util

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.util.Math.arcHav
import com.egoriku.grodnoroads.location.util.Math.havDistance
import com.egoriku.grodnoroads.location.util.Math.toDegrees
import com.egoriku.grodnoroads.location.util.Math.toRadians
import com.egoriku.grodnoroads.location.util.Math.wrap
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

object SphericalUtil {
    private const val EARTH_RADIUS = 6371009.0

    fun computeHeading(from: LatLng, to: LatLng): Double {
        val fromLat: Double = toRadians(from.latitude)
        val fromLng: Double = toRadians(from.longitude)
        val toLat: Double = toRadians(to.latitude)
        val toLng: Double = toRadians(to.longitude)
        val dLng = toLng - fromLng
        val heading = atan2(
            sin(dLng) * cos(toLat),
            cos(fromLat) * sin(toLat) - sin(fromLat) * cos(toLat) * cos(dLng)
        )
        return wrap(
            n = toDegrees(heading),
            min = -180.0,
            max = 180.0
        )
    }

    fun computeOffset(from: LatLng, distance: Double, heading: Double): LatLng {
        val d = distance / EARTH_RADIUS
        val h = toRadians(heading)
        // http://williams.best.vwh.net/avform.htm#LL
        val fromLat: Double = toRadians(from.latitude)
        val fromLng: Double = toRadians(from.longitude)
        val cosDistance = cos(d)
        val sinDistance = sin(d)
        val sinFromLat = sin(fromLat)
        val cosFromLat = cos(fromLat)
        val sinLat = cosDistance * sinFromLat + sinDistance * cosFromLat * cos(h)
        val dLng = atan2(
            sinDistance * cosFromLat * sin(h),
            cosDistance - sinFromLat * sinLat
        )
        return LatLng(
            latitude = toDegrees(asin(sinLat)),
            longitude = toDegrees(fromLng + dLng)
        )
    }

    fun computeDistanceBetween(from: LatLng, to: LatLng): Double {
        return computeAngleBetween(from, to) * EARTH_RADIUS
    }

    private fun computeAngleBetween(from: LatLng, to: LatLng): Double {
        return distanceRadians(
            lat1 = toRadians(from.latitude),
            lng1 = toRadians(from.longitude),
            lat2 = toRadians(to.latitude),
            lng2 = toRadians(to.longitude)
        )
    }

    private fun distanceRadians(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        return arcHav(
            havDistance(
                lat1 = lat1,
                lat2 = lat2,
                dLng = lng1 - lng2
            )
        )
    }
}
