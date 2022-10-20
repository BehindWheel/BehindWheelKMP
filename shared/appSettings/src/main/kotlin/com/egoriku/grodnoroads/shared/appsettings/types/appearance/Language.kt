package com.egoriku.grodnoroads.shared.appsettings.types.appearance

import com.egoriku.grodnoroads.resources.R

enum class Language(val lang: String) {
    Russian("ru"),
    English("en"),
    Belarusian("be");

    companion object {
        fun localeToLanguage(value: String) = checkNotNull(values().find { it.lang == value })

        fun Language.toStringResource() = when (this) {
            Russian -> R.string.appearance_app_language_ru
            English -> R.string.appearance_app_language_en
            Belarusian -> R.string.appearance_app_language_be
        }
    }
}