package com.egoriku.grodnoroads.shared.persistent.debug

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

private val SHOW_MAP_DEBUG_OVERLAY = booleanPreferencesKey("show_map_debug_overlay")

val Preferences.showMapDebugOverlay: Boolean
    get() = this[SHOW_MAP_DEBUG_OVERLAY] ?: false

fun MutablePreferences.updateShowMapDebugOverlay(value: Boolean) {
    this[SHOW_MAP_DEBUG_OVERLAY] = value
}
