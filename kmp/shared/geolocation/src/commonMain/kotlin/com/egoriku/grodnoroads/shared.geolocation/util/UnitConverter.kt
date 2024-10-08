package com.egoriku.grodnoroads.shared.geolocation.util

private const val KM_PER_HOUR_MULTIPLY = 3.6f

internal fun Float.toKilometersPerHour(): Int = (this * KM_PER_HOUR_MULTIPLY).toInt()
internal fun Double.toKilometersPerHour(): Int = (this * KM_PER_HOUR_MULTIPLY).toInt()
