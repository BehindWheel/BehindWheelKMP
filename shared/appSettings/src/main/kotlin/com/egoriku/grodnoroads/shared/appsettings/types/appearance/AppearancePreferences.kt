package com.egoriku.grodnoroads.shared.appsettings.types.appearance

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.egoriku.grodnoroads.shared.appsettings.extension.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val APP_THEME = intPreferencesKey("app_theme")

val Context.appTheme: Flow<Theme>
    get() = dataStore.data.map { it.appTheme }

val Preferences.appTheme: Theme
    get() = Theme.fromOrdinal(this[APP_THEME] ?: Theme.System.theme)

fun MutablePreferences.updateAppTheme(theme: Int) {
    this[APP_THEME] = theme
}
