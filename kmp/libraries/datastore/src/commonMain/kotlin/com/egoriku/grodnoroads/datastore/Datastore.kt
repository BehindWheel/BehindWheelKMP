@file:Suppress("ktlint:standard:property-naming")

package com.egoriku.grodnoroads.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "settings.preferences_pb"

fun createDataStore(
    producePath: () -> String
): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = null,
    migrations = emptyList(),
    produceFile = { producePath().toPath() }
)

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

suspend inline fun DataStore<Preferences>.edit(
    noinline transform: suspend MutablePreferences.() -> Unit
) = edit(transform)
