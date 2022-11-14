package com.egoriku.grodnoroads.screen.settings.whatsnew.util

import java.util.Date

val Date.ddMMMyyyy: String
    get() = Formatter.dayMonthYearFormatter.format(this)