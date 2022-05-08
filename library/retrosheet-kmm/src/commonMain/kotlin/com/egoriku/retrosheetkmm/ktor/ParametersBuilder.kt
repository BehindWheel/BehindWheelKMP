package com.egoriku.retrosheetkmm.ktor

import io.ktor.http.*

fun ParametersBuilder.replace(key: String, newValue: String) {
    remove(key)
    append(key, newValue)
}