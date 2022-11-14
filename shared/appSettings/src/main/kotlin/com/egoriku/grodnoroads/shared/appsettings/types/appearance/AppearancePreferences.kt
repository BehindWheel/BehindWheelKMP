package com.egoriku.grodnoroads.shared.appsettings.types.appearance

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey

private val APP_THEME = intPreferencesKey("app_theme")

val Preferences.appTheme: Theme
    get() = Theme.fromOrdinal(this[APP_THEME] ?: Theme.System.theme)

fun MutablePreferences.updateAppTheme(theme: Int) {
    this[APP_THEME] = theme
}
