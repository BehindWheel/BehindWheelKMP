package com.egoriku.grodnoroads.location.calc

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.util.SphericalUtil

fun computeOffset(from: LatLng, distance: Double, heading: Double): LatLng =
    SphericalUtil.computeOffset(from, distance, heading)
