@file:Suppress("NOTHING_TO_INLINE")

package com.egoriku.grodnoroads.maps.core.extension

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlin.math.roundToInt

infix fun LatLng.roundDistanceTo(latLng: LatLng): Int = computeDistance(this, latLng).roundToInt()
infix fun LatLng.distanceTo(latLng: LatLng): Double = computeDistance(this, latLng)
infix fun LatLng.headingTo(latLng: LatLng): Double = computeHeading(this, latLng)

inline fun computeOffset(from: LatLng, distance: Double, heading: Double): LatLng =
    SphericalUtil.computeOffset(from, distance, heading)

private fun computeHeading(from: LatLng, to: LatLng) = SphericalUtil.computeHeading(from, to)
private fun computeDistance(from: LatLng, to: LatLng) = SphericalUtil.computeDistanceBetween(from, to)
