package com.egoriku.grodnoroads.shared.persistent.map.mapstyle

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

private val IS_SHOW_TRAFFIC_JAM_APPEARANCE = booleanPreferencesKey("is_show_traffic_jam_appearance")
private val MAP_TYPE = intPreferencesKey("map_type")

val Preferences.trafficJamOnMap: Boolean
    get() = this[IS_SHOW_TRAFFIC_JAM_APPEARANCE] ?: false

val Preferences.mapType: MapType
    get() = MapType.toMapType(this[MAP_TYPE])

fun MutablePreferences.updateTrafficJamAppearance(value: Boolean) {
    this[IS_SHOW_TRAFFIC_JAM_APPEARANCE] = value
}

fun MutablePreferences.updateMapType(mapType: MapType) {
    this[MAP_TYPE] = mapType.type
}
