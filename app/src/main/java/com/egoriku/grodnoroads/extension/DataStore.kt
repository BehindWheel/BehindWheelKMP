package com.egoriku.grodnoroads.extension

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*

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