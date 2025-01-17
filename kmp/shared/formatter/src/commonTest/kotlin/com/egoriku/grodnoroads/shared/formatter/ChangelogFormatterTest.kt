package com.egoriku.grodnoroads.shared.formatter

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

class ChangelogFormatterTest {

    @Test
    fun formatterTest() {
        assertEquals(
            expected = "10 March, 2023",
            actual = ChangelogFormatter.format(1678395600000)
        )
        assertEquals(
            expected = "5 January, 2023",
            actual = ChangelogFormatter.format(
                timestamp = LocalDate(
                    year = 2023,
                    monthNumber = 1,
                    dayOfMonth = 5
                ).atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
            )
        )
    }
}
