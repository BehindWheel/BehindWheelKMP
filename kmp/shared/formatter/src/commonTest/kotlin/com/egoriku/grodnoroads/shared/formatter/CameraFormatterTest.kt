package com.egoriku.grodnoroads.shared.formatter

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlin.test.Test
import kotlin.test.assertEquals

class CameraFormatterTest {

    @Test
    fun formatterTest() {
        assertEquals(
            expected = "05.10.2024",
            actual = CameraFormatter.format(
                timestamp = LocalDate(
                    year = 2024,
                    monthNumber = 10,
                    dayOfMonth = 5
                ).atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
            )
        )
        assertEquals(
            expected = "05.01.2023",
            actual = CameraFormatter.format(
                timestamp = LocalDate(
                    year = 2023,
                    monthNumber = 1,
                    dayOfMonth = 5
                ).atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
            )
        )
    }
}