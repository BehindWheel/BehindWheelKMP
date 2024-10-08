package com.egoriku.grodnoroads.shared.persistent.map.filtering

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey

private val FILTER_EVENTS_TIME_PERIOD = longPreferencesKey("filter_events_time_period")

val Preferences.filteringMarkers: Filtering
    get() = Filtering.entries
        .firstOrNull { it.timeInMilliseconds == this[FILTER_EVENTS_TIME_PERIOD] }
        ?: Filtering.Hours1

fun MutablePreferences.updateFiltering(filtering: Filtering) {
    this[FILTER_EVENTS_TIME_PERIOD] = filtering.timeInMilliseconds
}
