package com.egoriku.grodnoroads.shared.persistent

import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme

fun Language.toStringResource() = when (this) {
    Language.Russian -> R.string.appearance_app_language_ru
    Language.English -> R.string.appearance_app_language_en
    Language.Belarusian -> R.string.appearance_app_language_be
    Language.System -> R.string.appearance_app_language_system
}

fun Theme.toStringResource() = when (this) {
    Theme.System -> R.string.appearance_app_theme_system
    Theme.Dark -> R.string.appearance_app_theme_dark
    Theme.Light -> R.string.appearance_app_theme_light
}