package com.egoriku.grodnoroads.domain.model

enum class Source(val source: String) {
    App("app"), Viber("viber");

    companion object {
        fun sourceFromString(value: String): Source {
            return requireNotNull(values().find { it.source == value })
        }
    }
}