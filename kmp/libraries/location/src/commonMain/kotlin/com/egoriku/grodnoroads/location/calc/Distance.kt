package com.egoriku.grodnoroads.location.calc

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.util.SphericalUtil
import kotlin.math.roundToInt

infix fun LatLng.roundDistanceTo(latLng: LatLng): Int =
    computeDistance(this, latLng).roundToInt()

private fun computeDistance(from: LatLng, to: LatLng) =
    SphericalUtil.computeDistanceBetween(from, to)