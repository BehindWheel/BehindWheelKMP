package com.egoriku.retrosheetkmm

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

actual fun parseCsv(data: String): List<Map<String, String>> {
    return csvReader()
        .readAllWithHeader(data)
        .map { map ->
            map.filterKeys { key -> key.isNotEmpty() }
        }
}
