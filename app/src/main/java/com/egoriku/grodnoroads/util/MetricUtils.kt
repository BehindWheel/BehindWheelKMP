package com.egoriku.grodnoroads.util

object MetricUtils {

    private const val KM_PER_HOUR_MULTIPLY = 3.6

    fun speedToKilometerPerHour(speedInMetersPerSeconds: Float) =
        (speedInMetersPerSeconds * KM_PER_HOUR_MULTIPLY).toInt()
}