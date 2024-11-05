package com.egoriku.grodnoroads.foundation.core

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf

// TODO: migrate to LocalActivity
// https://developer.android.com/jetpack/androidx/releases/activity#1.10.0-alpha03
val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("no any LocalActivity in composition")
}
