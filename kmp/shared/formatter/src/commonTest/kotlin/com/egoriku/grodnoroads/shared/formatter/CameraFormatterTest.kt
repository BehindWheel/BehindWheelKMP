package com.egoriku.grodnoroads.shared.formatter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

class CameraFormatterTest {

    @OptIn(ExperimentalTime::class)
    @Test
    fun formatterTest() {
        assertEquals(
            expected = "05.10.2024",
            actual = CameraFormatter.format(
                timestamp = LocalDate(
                    year = 2024,
                    month = 10,
                    day = 5
                ).atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
            )
        )
        assertEquals(
            expected = "05.01.2023",
            actual = CameraFormatter.format(
                timestamp = LocalDate(
                    year = 2023,
                    month = 1,
                    day = 5
                ).atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
            )
        )
    }
}
