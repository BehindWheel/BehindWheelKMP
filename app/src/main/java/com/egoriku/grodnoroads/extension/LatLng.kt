package com.egoriku.grodnoroads.extension

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.roundToInt

infix fun LatLng.distanceTo(latLng: LatLng): Int = computeDistance(this, latLng)

private fun computeDistance(
    position: LatLng,
    computedOffset: LatLng
) = SphericalUtil.computeDistanceBetween(position, computedOffset).roundToInt()

