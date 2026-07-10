package com.egoriku.grodnoroads.guidance.domain.model

import com.egoriku.grodnoroads.guidance.domain.model.DynamicZoomCalculatorImpl.Companion.MAX_DYNAMIC_ZOOM_DEFAULT
import com.egoriku.grodnoroads.guidance.domain.model.DynamicZoomCalculatorImpl.Companion.MAX_SPEED_FOR_ZOOM_KMH_DEFAULT
import com.egoriku.grodnoroads.guidance.domain.model.DynamicZoomCalculatorImpl.Companion.MIN_DYNAMIC_ZOOM_DEFAULT
import com.egoriku.grodnoroads.guidance.domain.model.DynamicZoomCalculatorImpl.Companion.MIN_SPEED_THRESHOLD_KMH_DEFAULT
import com.egoriku.grodnoroads.guidance.domain.model.DynamicZoomCalculatorImpl.Companion.RE_MIN_SPEED_THRESHOLD_KMH_DEFAULT
import kotlin.math.roundToInt
import kotlin.test.Test
import kotlin.test.assertEquals

class DynamicZoomCalculatorTest {

    private val calculator = DynamicZoomCalculatorImpl()

    @Test
    fun `calculateZoomLevel below threshold returns max zoom`() {
        val speedBelowThreshold = MIN_SPEED_THRESHOLD_KMH_DEFAULT - 1
        val zoom = calculator.calculateZoomLevel(speedBelowThreshold)
        assertEquals(MAX_DYNAMIC_ZOOM_DEFAULT, zoom)
    }

    @Test
    fun `calculateZoomLevel at threshold returns max zoom`() {
        val zoom = calculator.calculateZoomLevel(MIN_SPEED_THRESHOLD_KMH_DEFAULT)
        assertEquals(MAX_DYNAMIC_ZOOM_DEFAULT, zoom)
    }

    @Test
    fun `calculateZoomLevel at 0 kmh returns max zoom`() {
        val zoom = calculator.calculateZoomLevel(0)
        assertEquals(MAX_DYNAMIC_ZOOM_DEFAULT, zoom)
    }

    @Test
    fun `calculateZoomLevel at max speed returns min zoom`() {
        val zoom = calculator.calculateZoomLevel(MAX_SPEED_FOR_ZOOM_KMH_DEFAULT.toInt())
        assertEquals(MIN_DYNAMIC_ZOOM_DEFAULT, zoom)
    }

    @Test
    fun `calculateZoomLevel above max speed clamps to min zoom`() {
        val zoom = calculator.calculateZoomLevel(200)
        assertEquals(MIN_DYNAMIC_ZOOM_DEFAULT, zoom)
    }

    @Test
    fun `calculateZoomLevel at half max speed returns mid zoom`() {
        val halfSpeed = (MAX_SPEED_FOR_ZOOM_KMH_DEFAULT / 2).toInt()
        val expectedZoom = MAX_DYNAMIC_ZOOM_DEFAULT - (MAX_DYNAMIC_ZOOM_DEFAULT - MIN_DYNAMIC_ZOOM_DEFAULT) * 0.5f
        val expectedRounded = (expectedZoom * 10).roundToInt() / 10f
        val zoom = calculator.calculateZoomLevel(halfSpeed)
        assertEquals(expectedRounded, zoom)
    }

    @Test
    fun `calculateZoomLevel respects custom min dynamic zoom`() {
        val customCalculator = DynamicZoomCalculatorImpl(minDynamicZoom = 14f, maxDynamicZoom = 17f)
        val zoom = customCalculator.calculateZoomLevel(MAX_SPEED_FOR_ZOOM_KMH_DEFAULT.toInt())
        assertEquals(14f, zoom)
    }

    @Test
    fun `calculateZoomLevel respects custom max dynamic zoom`() {
        val customCalculator = DynamicZoomCalculatorImpl(minDynamicZoom = 12f, maxDynamicZoom = 18f)
        val zoom = customCalculator.calculateZoomLevel(0)
        assertEquals(18f, zoom)
    }

    @Test
    fun `calculateZoomLevel respects custom max speed for zoom`() {
        val customCalculator = DynamicZoomCalculatorImpl(maxSpeedForZoomKmh = 100f)
        val zoom = customCalculator.calculateZoomLevel(100)
        assertEquals(MIN_DYNAMIC_ZOOM_DEFAULT, zoom)
    }

    @Test
    fun `calculateZoomLevel respects custom min speed threshold`() {
        val customCalculator = DynamicZoomCalculatorImpl(minSpeedThresholdKmh = 10)
        val zoomBelowThreshold = customCalculator.calculateZoomLevel(9)
        val zoomAtThreshold = customCalculator.calculateZoomLevel(10)
        assertEquals(MAX_DYNAMIC_ZOOM_DEFAULT, zoomBelowThreshold)
        assertEquals(MAX_DYNAMIC_ZOOM_DEFAULT, zoomAtThreshold)
    }

    @Test
    fun `calculateZoomLevel returns rounded to one decimal place`() {
        // At 35 km/h with default settings: fraction = 35/140 = 0.25
        // zoom = 16.1 - (16.1 - 13) * 0.25 = 16.1 - 0.775 = 15.3
        val zoom = calculator.calculateZoomLevel(35)
        assertEquals(15.3f, zoom)
    }

    @Test
    fun `calculateZoomLevel below re-threshold returns max zoom`() {
        val zoom = calculator.calculateZoomLevel(RE_MIN_SPEED_THRESHOLD_KMH_DEFAULT - 1)
        assertEquals(MAX_DYNAMIC_ZOOM_DEFAULT, zoom)
    }

    @Test
    fun `calculateZoomLevel in hysteresis zone preserves previous zoom`() {
        calculator.calculateZoomLevel(60)
        val zoom = calculator.calculateZoomLevel(MIN_SPEED_THRESHOLD_KMH_DEFAULT)
        // Speed is in hysteresis zone (reMin < 25 <= min), should preserve last zoom
        val expectedAt60 = calculator.calculateZoomLevel(60)
        assertEquals(expectedAt60, zoom)
    }

    @Test
    fun `calculateZoomLevel speed spike above threshold then drop preserves zoom`() {
        // Simulate GPS spike: idle -> spike -> back to idle
        calculator.calculateZoomLevel(0)
        calculator.calculateZoomLevel(30) // spike above threshold
        val zoomAfterSpike = calculator.calculateZoomLevel(MIN_SPEED_THRESHOLD_KMH_DEFAULT)
        // Should preserve zoom from spike, not jump back to max
        val expectedAt30 = calculator.calculateZoomLevel(30)
        assertEquals(expectedAt30, zoomAfterSpike)
    }

    @Test
    fun `calculateZoomLevel speed drops below re-threshold returns max zoom`() {
        calculator.calculateZoomLevel(60) // set some zoom
        val zoom = calculator.calculateZoomLevel(RE_MIN_SPEED_THRESHOLD_KMH_DEFAULT - 1)
        assertEquals(MAX_DYNAMIC_ZOOM_DEFAULT, zoom)
    }
}
