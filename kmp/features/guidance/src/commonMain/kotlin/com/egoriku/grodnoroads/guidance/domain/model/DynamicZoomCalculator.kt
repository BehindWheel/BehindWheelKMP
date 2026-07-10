package com.egoriku.grodnoroads.guidance.domain.model

import kotlin.math.roundToInt

/**
 * Calculates dynamic map zoom level based on current speed.
 * Higher speed = lower zoom (more context ahead).
 *
 * Uses hysteresis to prevent zoom jitter from GPS noise:
 * zoom decreases when speed rises above [minSpeedThresholdKmh],
 * but only returns to max when speed drops below [reMinSpeedThresholdKmh].
 */
interface DynamicZoomCalculator {
    fun calculateZoomLevel(speedKmh: Int): Float
}

class DynamicZoomCalculatorImpl(
    private val minDynamicZoom: Float = MIN_DYNAMIC_ZOOM_DEFAULT,
    private val maxDynamicZoom: Float = MAX_DYNAMIC_ZOOM_DEFAULT,
    private val maxSpeedForZoomKmh: Float = MAX_SPEED_FOR_ZOOM_KMH_DEFAULT,
    private val minSpeedThresholdKmh: Int = MIN_SPEED_THRESHOLD_KMH_DEFAULT,
    private val reMinSpeedThresholdKmh: Int = RE_MIN_SPEED_THRESHOLD_KMH_DEFAULT
) : DynamicZoomCalculator {

    companion object {
        const val MIN_DYNAMIC_ZOOM_DEFAULT = 13f
        const val MAX_DYNAMIC_ZOOM_DEFAULT = 16.1f
        const val MAX_SPEED_FOR_ZOOM_KMH_DEFAULT = 140f
        const val MIN_SPEED_THRESHOLD_KMH_DEFAULT = 25
        const val RE_MIN_SPEED_THRESHOLD_KMH_DEFAULT = 15
    }

    private var lastZoom: Float = maxDynamicZoom

    override fun calculateZoomLevel(speedKmh: Int): Float {
        val newZoom = when {
            speedKmh <= reMinSpeedThresholdKmh -> maxDynamicZoom
            speedKmh <= minSpeedThresholdKmh -> lastZoom
            else -> {
                val speedFraction = (speedKmh / maxSpeedForZoomKmh).coerceIn(0f, 1f)
                val zoom = maxDynamicZoom - (maxDynamicZoom - minDynamicZoom) * speedFraction
                (zoom * 10).roundToInt() / 10f
            }
        }

        lastZoom = newZoom
        return newZoom
    }
}
