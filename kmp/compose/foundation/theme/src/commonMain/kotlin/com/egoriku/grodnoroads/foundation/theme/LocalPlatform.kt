package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.runtime.compositionLocalOf

val LocalPlatform = compositionLocalOf<Platform> { error("LocalPlatform not set") }

enum class Platform {
    Android,
    IOS
}
