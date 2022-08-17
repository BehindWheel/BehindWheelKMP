package com.egoriku.grodnoroads.util

import androidx.annotation.VisibleForTesting
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    @VisibleForTesting
    var defaultTimeZone: TimeZone = TimeZone.getTimeZone("Europe/Minsk")

    private val simpleDateFormat: SimpleDateFormat
        get() = SimpleDateFormat("H:mm", Locale.getDefault()).apply {
            timeZone = defaultTimeZone
        }

    fun formatToTime(date: Long): String = simpleDateFormat.format(Date(date))
}