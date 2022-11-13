package com.egoriku.grodnoroads.settings.appearance.domain.util

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

val currentAppLanguage: String?
    get() = AppCompatDelegate.getApplicationLocales()[0]?.language

fun resetAppLanguage() {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
}

fun setAppLanguage(language: String) {
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
}