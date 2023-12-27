package com.egoriku.grodnoroads.location

import com.egoriku.grodnoroads.location.util.SphericalUtil
import kotlin.math.roundToInt

data class LatLng(val latitude: Double, val longitude: Double)

infix fun LatLng.roundDistanceTo(latLng: LatLng): Int = computeDistance(this, latLng).roundToInt()

inline fun computeOffset(from: LatLng, distance: Double, heading: Double): LatLng =
    SphericalUtil.computeOffset(from, distance, heading)

private fun computeDistance(from: LatLng, to: LatLng) = SphericalUtil.computeDistanceBetween(from, to)
