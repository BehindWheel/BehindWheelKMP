package com.egoriku.grodnoroads.settings.whatsnew.domain.util

import java.util.Date

val Date.ddMMMyyyy: String
    get() = Formatter.dayMonthYearFormatter.format(this)