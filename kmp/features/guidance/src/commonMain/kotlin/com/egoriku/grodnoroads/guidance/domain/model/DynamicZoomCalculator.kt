package com.egoriku.grodnoroads.guidance.domain.model

import kotlin.math.roundToInt

/**
 * Calculates dynamic map zoom level based on current speed.
 * Higher speed = lower zoom (more context ahead).
 */
interface DynamicZoomCalculator {
    fun calculateZoomLevel(speedKmh: Int): Float
}

class DynamicZoomCalculatorImpl(
    private val minDynamicZoom: Float = MIN_DYNAMIC_ZOOM_DEFAULT,
    private val maxDynamicZoom: Float = MAX_DYNAMIC_ZOOM_DEFAULT,
    private val maxSpeedForZoomKmh: Float = MAX_SPEED_FOR_ZOOM_KMH_DEFAULT,
    private val minSpeedThresholdKmh: Int = MIN_SPEED_THRESHOLD_KMH_DEFAULT
) : DynamicZoomCalculator {

    companion object {
        const val MIN_DYNAMIC_ZOOM_DEFAULT = 13f
        const val MAX_DYNAMIC_ZOOM_DEFAULT = 17.5f
        const val MAX_SPEED_FOR_ZOOM_KMH_DEFAULT = 140f
        const val MIN_SPEED_THRESHOLD_KMH_DEFAULT = 5
    }

    override fun calculateZoomLevel(speedKmh: Int): Float {
        // At or below threshold, treat as stopped (max zoom)
        if (speedKmh <= minSpeedThresholdKmh) {
            return maxDynamicZoom
        }

        val speedFraction = (speedKmh / maxSpeedForZoomKmh).coerceIn(0f, 1f)
        val zoom = maxDynamicZoom - (maxDynamicZoom - minDynamicZoom) * speedFraction
        return (zoom * 10).roundToInt() / 10f
    }
}
