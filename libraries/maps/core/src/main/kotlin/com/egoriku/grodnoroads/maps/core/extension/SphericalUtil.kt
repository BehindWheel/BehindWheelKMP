@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.grodnoroads.maps.core.extension

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.roundToInt

infix fun LatLng.distanceTo(latLng: LatLng): Int = computeDistance(this, latLng)

private fun computeDistance(
    position: LatLng,
    computedOffset: LatLng
) = SphericalUtil.computeDistanceBetween(position, computedOffset).roundToInt()

inline fun computeOffset(from: LatLng, distance: Double, heading: Double): LatLng {
    return SphericalUtil.computeOffset(from, distance, heading)
}
