package com.egoriku.grodnoroads.screen.settings.store.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val APP_THEME = intPreferencesKey("app_theme")

val IS_SHOW_STATIONARY_CAMERAS = booleanPreferencesKey("is_show_stationary_cameras")
val IS_SHOW_MOBILE_CAMERAS = booleanPreferencesKey("is_show_mobile_cameras")
val IS_SHOW_TRAFFIC_POLICE_EVENTS = booleanPreferencesKey("is_show_traffic_police_events")
val IS_SHOW_TRAFFIC_JAM = booleanPreferencesKey("is_show_traffic_jam")
val IS_SHOW_INCIDENT_EVENTS = booleanPreferencesKey("is_show_incident_events")

val ALERT_DISTANCE = intPreferencesKey("alert_distance")