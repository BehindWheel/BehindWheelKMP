package com.egoriku.grodnoroads.shared.persistent.map.drivemode

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey

const val DEFAULT_MAP_ZOOM_IN_CITY = 14.8f
const val DEFAULT_MAP_ZOOM_OUT_CITY = 13.0f
const val DEFAULT_DYNAMIC_ZOOM_ENABLED = true

private val MAP_ZOOM_IN_CITY = floatPreferencesKey("map_zoom_in_city")
private val MAP_ZOOM_OUTSIDE_CITY = floatPreferencesKey("map_zoom_outside_city")
private val DYNAMIC_ZOOM_ENABLED = booleanPreferencesKey("dynamic_zoom_enabled")
private val AUTO_START_DRIVE_MODE = booleanPreferencesKey("auto_start_drive_mode")

val Preferences.mapZoomInCity: Float
    get() = this[MAP_ZOOM_IN_CITY] ?: DEFAULT_MAP_ZOOM_IN_CITY

val Preferences.mapZoomOutCity: Float
    get() = this[MAP_ZOOM_OUTSIDE_CITY] ?: DEFAULT_MAP_ZOOM_OUT_CITY

val Preferences.dynamicZoomEnabled: Boolean
    get() = this[DYNAMIC_ZOOM_ENABLED] ?: DEFAULT_DYNAMIC_ZOOM_ENABLED

val Preferences.autoStartDriveMode: Boolean
    get() = this[AUTO_START_DRIVE_MODE] ?: false

fun MutablePreferences.updateMapZoomInCity(value: Float) {
    this[MAP_ZOOM_IN_CITY] = value
}

fun MutablePreferences.updateMapZoomOutsideCity(value: Float) {
    this[MAP_ZOOM_OUTSIDE_CITY] = value
}

fun MutablePreferences.updateDynamicZoomEnabled(value: Boolean) {
    this[DYNAMIC_ZOOM_ENABLED] = value
}

fun MutablePreferences.updateAutoStartDriveMode(value: Boolean) {
    this[AUTO_START_DRIVE_MODE] = value
}
