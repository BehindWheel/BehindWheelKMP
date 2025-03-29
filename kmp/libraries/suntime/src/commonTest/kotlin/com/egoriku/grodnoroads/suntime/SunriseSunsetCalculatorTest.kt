package com.egoriku.grodnoroads.suntime

import com.egoriku.grodnoroads.suntime.SunriseSunsetCalculator.Twilight
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone

class SunriseSunsetCalculatorTest {

    @Test
    fun `calculate for Grodno`() {
        val sunTimeOfficial = SunriseSunsetCalculator.calculate(
            date = LocalDateTime(2025, 3, 23, 0, 0),
            latitude = 53.6687765,
            longitude = 23.8212226,
            twilight = Twilight.Official,
            timeZone = TimeZone.of("Europe/Minsk")
        )
        assertNotNull(sunTimeOfficial)
        assertEquals(expected = LocalTime(7, 21), actual = sunTimeOfficial.sunrise)
        assertEquals(expected = LocalTime(19, 42), actual = sunTimeOfficial.sunset)

        val sunTimeCustom = SunriseSunsetCalculator.calculate(
            date = LocalDateTime(2025, 3, 23, 0, 0),
            latitude = 53.6687765,
            longitude = 23.8212226,
            twilight = Twilight.Custom(2.0),
            timeZone = TimeZone.of("Europe/Minsk")
        )
        assertNotNull(sunTimeCustom)
        assertEquals(expected = LocalTime(7, 40), actual = sunTimeCustom.sunrise)
        assertEquals(expected = LocalTime(19, 23), actual = sunTimeCustom.sunset)
    }

    @Test
    fun `calculate Minsk on summer solstice`() {
        val sunTimeOfficial = SunriseSunsetCalculator.calculate(
            date = LocalDateTime(2025, 6, 21, 0, 0),
            latitude = 53.9,
            longitude = 27.5667,
            twilight = Twilight.Official,
            timeZone = TimeZone.of("Europe/Minsk")
        )
        assertNotNull(sunTimeOfficial)
        assertEquals(LocalTime(4, 37, 0), sunTimeOfficial.sunrise)
        assertEquals(LocalTime(21, 45, 0), sunTimeOfficial.sunset)

        val sunTimeCustom = SunriseSunsetCalculator.calculate(
            date = LocalDateTime(2025, 6, 21, 0, 0),
            latitude = 53.9,
            longitude = 27.5667,
            twilight = Twilight.Custom(2.0),
            timeZone = TimeZone.of("Europe/Minsk")
        )
        assertNotNull(sunTimeCustom)
        assertEquals(LocalTime(5, 3, 0), sunTimeCustom.sunrise)
        assertEquals(LocalTime(21, 19, 0), sunTimeCustom.sunset)
    }

    @Test
    fun `calculate Minsk on winter solstice`() {
        val sunTimeOfficial = SunriseSunsetCalculator.calculate(
            date = LocalDateTime(2025, 12, 21, 0, 0),
            latitude = 53.9,
            longitude = 27.5667,
            twilight = Twilight.Official,
            timeZone = TimeZone.of("Europe/Minsk")
        )
        assertNotNull(sunTimeOfficial)
        assertEquals(LocalTime(9, 25, 0), sunTimeOfficial.sunrise)
        assertEquals(LocalTime(16, 49, 0), sunTimeOfficial.sunset)

        val sunTimeCustom = SunriseSunsetCalculator.calculate(
            date = LocalDateTime(2025, 12, 21, 0, 0),
            latitude = 53.9,
            longitude = 27.5667,
            twilight = Twilight.Custom(2.0),
            timeZone = TimeZone.of("Europe/Minsk")
        )
        assertNotNull(sunTimeCustom)
        assertEquals(LocalTime(9, 52, 0), sunTimeCustom.sunrise)
        assertEquals(LocalTime(16, 22, 0), sunTimeCustom.sunset)
    }

    @Test
    fun `polar coordinates with no sunrise or sunset`() {
        val sunTime = SunriseSunsetCalculator.calculate(
            date = LocalDateTime(2025, 6, 21, 0, 0),
            latitude = 89.0,
            longitude = 0.0,
            twilight = Twilight.Custom(2.0),
            timeZone = TimeZone.of("Europe/Minsk")
        )
        assertNull(sunTime)
    }
}
