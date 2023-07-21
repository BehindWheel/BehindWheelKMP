package com.egoriku.grodnoroads.shared.appsettings.types.appearance

import com.egoriku.grodnoroads.resources.R

enum class Language(val lang: String) {
    System("system"),
    Russian("ru"),
    English("en"),
    Belarusian("be");

    companion object {
        fun localeToLanguage(value: String?) = entries.find { it.lang == value } ?: System

        fun Language.toStringResource() = when (this) {
            Russian -> R.string.appearance_app_language_ru
            English -> R.string.appearance_app_language_en
            Belarusian -> R.string.appearance_app_language_be
            System -> R.string.appearance_app_language_system
        }
    }
}