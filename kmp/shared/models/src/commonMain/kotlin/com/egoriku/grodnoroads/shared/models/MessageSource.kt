package com.egoriku.grodnoroads.shared.models

import androidx.compose.runtime.Stable

@Stable
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