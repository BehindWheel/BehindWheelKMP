package com.egoriku.grodnoroads.shared.appsettings.types.appearance

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.egoriku.grodnoroads.shared.appsettings.extension.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val APP_THEME = intPreferencesKey("app_theme")
private val APP_LANGUAGE = stringPreferencesKey("app_language")

val Context.appTheme: Flow<Theme>
    get() = dataStore.data.map { it.appTheme }

val Context.appLanguage: Flow<Language>
    get() = dataStore.data.map { it.language }

val Preferences.appTheme: Theme
    get() = Theme.fromOrdinal(this[APP_THEME] ?: Theme.System.theme)

val Preferences.language: Language
    get() = Language.localeToLanguage(this[APP_LANGUAGE] ?: Language.Russian.lang)

fun MutablePreferences.updateLanguage(lang: String) {
    this[APP_LANGUAGE] = lang
}

fun MutablePreferences.updateAppTheme(theme: Int) {
    this[APP_THEME] = theme
}
