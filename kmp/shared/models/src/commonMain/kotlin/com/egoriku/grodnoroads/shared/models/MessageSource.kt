package com.egoriku.grodnoroads.shared.models

enum class MessageSource(val source: String) {
    App("app"),
    Viber("viber"),
    Telegram("telegram");

    companion object {
        fun sourceFromString(value: String): MessageSource {
            return requireNotNull(entries.find { it.source == value })
        }
    }
}
