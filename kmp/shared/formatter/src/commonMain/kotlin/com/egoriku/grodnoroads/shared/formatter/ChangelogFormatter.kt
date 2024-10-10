package com.egoriku.grodnoroads.shared.formatter

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

object ChangelogFormatter {

    private val defaultTimeZone = TimeZone.of("Europe/Minsk")
    private val dayMonthYearFormatter = LocalDateTime.Format {
        dayOfMonth(padding = Padding.NONE)
        char(' ')
        monthName(MonthNames.ENGLISH_FULL)
        char(',')
        char(' ')
        year()
    }

    fun format(timestamp: Long): String =
        Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(defaultTimeZone)
            .format(dayMonthYearFormatter)
}
