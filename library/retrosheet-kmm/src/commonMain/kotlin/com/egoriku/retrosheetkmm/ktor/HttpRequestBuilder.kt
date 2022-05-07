package com.egoriku.retrosheetkmm.ktor

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.client.plugins.*

fun HttpRequestBuilder.sheet(value: Any?) = parameter("sheet", value)

fun HttpRequestBuilder.query(value: Any?) = parameter("tq", value)

fun DefaultRequest.DefaultRequestBuilder.defaultSheetUrl(docId: String) {
    url {
        protocol = URLProtocol.HTTPS
        host = "docs.google.com"
        encodedPath = "/spreadsheets/d/$docId"
    }
}