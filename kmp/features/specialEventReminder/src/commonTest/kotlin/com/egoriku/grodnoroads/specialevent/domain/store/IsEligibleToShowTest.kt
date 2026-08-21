package com.egoriku.grodnoroads.specialevent.domain.store

import com.egoriku.grodnoroads.suntime.SunriseSunsetCalculator.SunTime
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

class IsEligibleToShowTest {

    private val today = LocalDate(2026, 5, 27)
    private val sunTime = SunTime(sunrise = LocalTime(5, 0), sunset = LocalTime(21, 0))

    @Test
    fun `not eligible when dismissed today`() {
        val currentDateTime = LocalDateTime(today, LocalTime(12, 0))

        assertFalse(
            isEligibleToShow(
                currentDateTime = currentDateTime,
                dismissedDate = today.toString(),
                sunTime = sunTime
            )
        )
    }

    @Test
    fun `eligible when dismissed on a different day`() {
        val currentDateTime = LocalDateTime(today, LocalTime(12, 0))

        assertTrue(
            isEligibleToShow(
                currentDateTime = currentDateTime,
                dismissedDate = LocalDate(2026, 5, 26).toString(),
                sunTime = sunTime
            )
        )
    }

    @Test
    fun `eligible when sun time is unavailable regardless of time of day`() {
        val currentDateTime = LocalDateTime(today, LocalTime(2, 0))

        assertTrue(
            isEligibleToShow(
                currentDateTime = currentDateTime,
                dismissedDate = null,
                sunTime = null
            )
        )
    }

    @Test
    fun `eligible during day time`() {
        val currentDateTime = LocalDateTime(today, LocalTime(12, 0))

        assertTrue(
            isEligibleToShow(
                currentDateTime = currentDateTime,
                dismissedDate = null,
                sunTime = sunTime
            )
        )
    }

    @Test
    fun `not eligible before sunrise`() {
        val currentDateTime = LocalDateTime(today, LocalTime(4, 0))

        assertFalse(
            isEligibleToShow(
                currentDateTime = currentDateTime,
                dismissedDate = null,
                sunTime = sunTime
            )
        )
    }

    @Test
    fun `not eligible after sunset`() {
        val currentDateTime = LocalDateTime(today, LocalTime(22, 0))

        assertFalse(
            isEligibleToShow(
                currentDateTime = currentDateTime,
                dismissedDate = null,
                sunTime = sunTime
            )
        )
    }
}
