package com.egoriku.grodnoroads.shared.models

import androidx.compose.runtime.Stable

// TODO: rename to MessageSource
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