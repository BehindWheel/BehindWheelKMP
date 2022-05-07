package com.egoriku.retrosheetkmm.ktor

import io.ktor.http.*

private const val URL_START = "https://docs.google.com/spreadsheets"

val URLBuilder.iGoogleSheetUrl: Boolean
    get() = buildString().startsWith(URL_START)

fun Url.iGoogleSheetUrl(): Boolean = toString().startsWith(URL_START)