package com.egoriku.grodnoroads.shared.appsettings.types.alert

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey

private const val DEFAULT_ALERT_DISTANCE = 600

private val ALERT_DISTANCE = intPreferencesKey("alert_distance")

val Preferences.alertDistance: Int
    get() = this[ALERT_DISTANCE] ?: DEFAULT_ALERT_DISTANCE
