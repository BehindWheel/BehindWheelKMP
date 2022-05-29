package com.egoriku.grodnoroads.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("GMT");
    }

    fun formatToTime(date: Long): String = simpleDateFormat.format(Date(date))
}