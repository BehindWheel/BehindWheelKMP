package com.egoriku.retrosheetkmm.ktor.plugin

import com.egoriku.retrosheetkmm.util.ColumnNameVerifier
import com.egoriku.retrosheetkmm.util.SheetUtils

@DslMarker
annotation class ConfigDsl

class SheetBuilder {
    @ConfigDsl
    var name: String = ""

    @ConfigDsl
    var columns: List<String> = emptyList()

    fun build(): Sheet {
        val columnMap = SheetUtils.toLetterMap(columns)

        ColumnNameVerifier(columnMap.keys).verify()

        return Sheet(
            sheetName = name,
            columns = columnMap
        )
    }
}

class ConfigBuilder {

    private val sheets = mutableMapOf<String, Map<String, String>>()

    @ConfigDsl
    fun sheet(setup: SheetBuilder.() -> Unit = {}) {
        val sheet = SheetBuilder().apply(setup).build()

        sheets += sheet.sheetName to sheet.columns
    }

    fun build() = Configuration(sheets)
}

@ConfigDsl
fun configuration(setup: ConfigBuilder.() -> Unit): Configuration {
    return ConfigBuilder().apply(setup).build()
}

data class Configuration(val sheets: Map<String, Map<String, String>>)
data class Sheet(val sheetName: String, val columns: Map<String, String>)

