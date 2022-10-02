package com.egoriku.grodnoroads.map.domain.model

enum class Source(val source: String) {
    App("app"),
    Viber("viber"),
    Telegram("telegram"),
    Zello("zello");

    companion object {
        fun sourceFromString(value: String): Source {
            return requireNotNull(values().find { it.source == value })
        }
    }
}