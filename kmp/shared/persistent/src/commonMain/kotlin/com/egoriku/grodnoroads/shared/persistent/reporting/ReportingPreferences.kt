package com.egoriku.grodnoroads.shared.persistent.reporting

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

private val LAST_REPORT_TIME = longPreferencesKey("last_report_time")
private val REPORTS_IN_LAST_HOUR = intPreferencesKey("reports_in_last_hour")

val Preferences.lastReportTime: Long
    get() = this[LAST_REPORT_TIME] ?: 0L

val Preferences.reportsInLastHour: Int
    get() = this[REPORTS_IN_LAST_HOUR] ?: 0

fun MutablePreferences.updateLastReportTime(time: Long) {
    this[LAST_REPORT_TIME] = time
}

fun MutablePreferences.updateReportsInLastHour(value: Int) {
    this[REPORTS_IN_LAST_HOUR] = value
}
