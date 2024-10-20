package com.egoriku.grodnoroads.extensions

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

class DateTimeTest {

    @BeforeTest
    fun init() {
        DateTime.defaultTimeZone = TimeZone.UTC
    }

    @Test
    fun formatTest() {
        assertEquals(
            expected = "8:06",
            actual = DateTime.formatToTime((8.hours + 6.minutes).inWholeMilliseconds)
        )
        assertEquals(
            expected = "17:09",
            actual = DateTime.formatToTime((17.hours + 9.minutes).inWholeMilliseconds)
        )

        val date = LocalDateTime(
            year = 2024,
            monthNumber = 1,
            dayOfMonth = 17,
            hour = 17,
            minute = 9,
            second = 12
        ).toInstant(TimeZone.UTC).toEpochMilliseconds()
        assertEquals(
            expected = "17:09",
            actual = DateTime.formatToTime(date)
        )
    }
}
