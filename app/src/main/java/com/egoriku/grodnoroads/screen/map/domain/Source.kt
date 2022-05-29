package com.egoriku.grodnoroads.screen.map.domain

enum class Source(val source: String) {
    App("app"),
    Viber("viber"),
    Telegram("telegram");

    companion object {
        fun sourceFromString(value: String): Source {
            return requireNotNull(values().find { it.source == value })
        }
    }
}