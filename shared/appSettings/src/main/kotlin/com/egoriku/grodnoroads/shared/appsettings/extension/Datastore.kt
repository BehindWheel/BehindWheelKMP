package com.egoriku.grodnoroads.shared.appsettings.extension

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

suspend fun <T> DataStore<Preferences>.put(key: String, value: T) {
    edit {
        when (value) {
            is Int -> it[intPreferencesKey(key)] = value
            is Long -> it[longPreferencesKey(key)] = value
            is Double -> it[doublePreferencesKey(key)] = value
            is Float -> it[floatPreferencesKey(key)] = value
            is Boolean -> it[booleanPreferencesKey(key)] = value
            is String -> it[stringPreferencesKey(key)] = value
            else -> throw IllegalArgumentException("This type can be saved into DataStore")
        }
    }
}

// TODO: Migrate in all places
suspend inline fun DataStore<Preferences>.edit(
    noinline transform: suspend MutablePreferences.() -> Unit
) = edit(transform)