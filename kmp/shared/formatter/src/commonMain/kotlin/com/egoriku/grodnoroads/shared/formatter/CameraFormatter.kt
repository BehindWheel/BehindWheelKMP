package com.egoriku.grodnoroads.shared.formatter

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

object CameraFormatter {

    private val defaultTimeZone = TimeZone.of("Europe/Minsk")
    private val dateTimeFormat = LocalDateTime.Format {
        day(padding = Padding.ZERO)
        char('.')
        monthNumber()
        char('.')
        year()
    }

    @OptIn(ExperimentalTime::class)
    fun format(timestamp: Long): String {
        return Instant.fromEpochMilliseconds(timestamp)
            .toLocalDateTime(defaultTimeZone)
            .format(dateTimeFormat)
    }
}
