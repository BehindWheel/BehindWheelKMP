package com.egoriku.grodnoroads.extensions

import androidx.annotation.VisibleForTesting
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DateTime {

    @VisibleForTesting
    var defaultTimeZone = TimeZone.of("Europe/Minsk")

    fun formatToTime(date: Long): String {
        val instant = Instant.fromEpochMilliseconds(date)
        val localDateTime = instant.toLocalDateTime(defaultTimeZone)

        return localDateTime.time.toString().trimStart('0')
    }

    fun currentTimeMillis(): Long = Clock.System.now().toEpochMilliseconds()
}