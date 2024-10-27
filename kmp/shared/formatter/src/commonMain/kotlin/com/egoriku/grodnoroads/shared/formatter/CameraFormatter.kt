package com.egoriku.grodnoroads.shared.formatter

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

object CameraFormatter {

    private val defaultTimeZone = TimeZone.of("Europe/Minsk")
    private val dateTimeFormat = LocalDateTime.Format {
        dayOfMonth()
        char('.')
        monthNumber()
        char('.')
        year()
    }

    fun format(timestamp: Long): String {
        return Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(defaultTimeZone)
            .format(dateTimeFormat)
    }
}
