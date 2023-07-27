package com.egoriku.grodnoroads.shared.appsettings.types.appearance

import com.egoriku.grodnoroads.resources.R

enum class Theme(val theme: Int) {
    System(0),
    Dark(1),
    Light(2);

    companion object {
        fun fromOrdinal(ordinal: Int) = entries[ordinal]

        fun Theme.toStringResource() = when (this) {
            System -> R.string.appearance_app_theme_system
            Dark -> R.string.appearance_app_theme_dark
            Light -> R.string.appearance_app_theme_light
        }
    }
}