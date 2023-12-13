package com.egoriku.grodnoroads.setting.changelog.domain.util

import java.util.Date

val Date.ddMMMyyyy: String
    get() = Formatter.dayMonthYearFormatter.format(this)