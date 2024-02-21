package com.egoriku.grodnoroads.shared.appsettings.types.map.filtering

import com.egoriku.grodnoroads.resources.R
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

enum class Filtering(val timeInMilliseconds: Long) {
    Minutes15(15.minutes.inWholeMilliseconds),
    Minutes30(30.minutes.inWholeMilliseconds),
    Minutes45(45.minutes.inWholeMilliseconds),
    Hours1(1.hours.inWholeMilliseconds);

    companion object {
        fun Filtering.toResource() = when (this) {
            Minutes15 -> R.string.map_markers_filtering_15_minutes
            Minutes30 -> R.string.map_markers_filtering_30_minutes
            Minutes45 -> R.string.map_markers_filtering_45_minutes
            Hours1 -> R.string.map_markers_filtering_1_hour
        }
    }
}