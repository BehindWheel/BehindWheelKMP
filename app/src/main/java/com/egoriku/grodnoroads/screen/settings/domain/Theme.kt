package com.egoriku.grodnoroads.screen.settings.domain

import com.egoriku.grodnoroads.R

enum class Theme(val theme: Int) {
    System(0),
    Dark(1),
    Light(2);

    companion object {
        fun fromOrdinal(ordinal: Int) = values()[ordinal]

        fun Theme.toStringResource() = when (this) {
            System -> R.string.settings_app_theme_system
            Dark -> R.string.settings_app_theme_dark
            Light -> R.string.settings_app_theme_light
        }
    }
}