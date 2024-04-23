package com.egoriku.grodnoroads.shared.persistent.map.filtering

import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

enum class Filtering(val timeInMilliseconds: Long) {
    Minutes15(15.minutes.inWholeMilliseconds),
    Minutes30(30.minutes.inWholeMilliseconds),
    Minutes45(45.minutes.inWholeMilliseconds),
    Hours1(1.hours.inWholeMilliseconds);
}