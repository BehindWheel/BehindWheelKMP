package com.egoriku.grodnoroads.location.calc

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.util.SphericalUtil

infix fun LatLng.headingTo(latLng: LatLng): Double = computeHeading(this, latLng)

private fun computeHeading(from: LatLng, to: LatLng) =
    SphericalUtil.computeHeading(from, to)
