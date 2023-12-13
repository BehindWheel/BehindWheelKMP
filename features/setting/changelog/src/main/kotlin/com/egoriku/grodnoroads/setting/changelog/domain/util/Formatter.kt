package com.egoriku.grodnoroads.setting.changelog.domain.util

import java.text.SimpleDateFormat
import java.util.Locale

internal object Formatter {

    val dayMonthYearFormatter by lazy {
        SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    }
}