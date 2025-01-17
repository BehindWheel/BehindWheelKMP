package com.egoriku.grodnoroads.shared.persistent.intro

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

private val SHOW_INTRO = booleanPreferencesKey("show_intro")

val Preferences.showIntro: Boolean
    get() = this[SHOW_INTRO] ?: true

fun MutablePreferences.showIntro(value: Boolean) {
    this[SHOW_INTRO] = value
}
