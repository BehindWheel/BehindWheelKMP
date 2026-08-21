package com.egoriku.grodnoroads.shared.persistent.specialevent

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

private val SPECIAL_EVENT_DISMISSED_DATE = stringPreferencesKey("special_event_dismissed_date")

val Preferences.specialEventDismissedDate: String?
    get() = this[SPECIAL_EVENT_DISMISSED_DATE]

fun MutablePreferences.updateSpecialEventDismissedDate(date: String) {
    this[SPECIAL_EVENT_DISMISSED_DATE] = date
}
