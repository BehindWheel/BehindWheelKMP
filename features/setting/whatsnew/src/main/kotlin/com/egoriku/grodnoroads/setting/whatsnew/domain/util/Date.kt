package com.egoriku.grodnoroads.setting.whatsnew.domain.util

import java.util.Date

val Date.ddMMMyyyy: String
    get() = Formatter.dayMonthYearFormatter.format(this)