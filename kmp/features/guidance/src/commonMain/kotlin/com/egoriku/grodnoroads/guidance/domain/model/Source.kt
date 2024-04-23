package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable

@Stable
enum class Source(val source: String) {
    App("app"),
    Viber("viber"),
    Telegram("telegram"),
    Zello("zello");

    companion object {
        fun sourceFromString(value: String): Source {
            return requireNotNull(entries.find { it.source == value })
        }
    }
}