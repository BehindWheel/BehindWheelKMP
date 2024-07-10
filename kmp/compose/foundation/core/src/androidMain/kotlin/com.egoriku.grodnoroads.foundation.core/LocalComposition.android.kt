package com.egoriku.grodnoroads.foundation.core

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("no any LocalActivity in composition")
}