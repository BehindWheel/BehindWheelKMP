package com.egoriku.grodnoroads.shared.persistent.map.mapstyle

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

private val IS_SHOW_TRAFFIC_JAM_APPEARANCE = booleanPreferencesKey("is_show_traffic_jam_appearance")

val Preferences.trafficJamOnMap: Boolean
    get() = this[IS_SHOW_TRAFFIC_JAM_APPEARANCE] ?: false

fun MutablePreferences.updateTrafficJamAppearance(value: Boolean) {
    this[IS_SHOW_TRAFFIC_JAM_APPEARANCE] = value
}
