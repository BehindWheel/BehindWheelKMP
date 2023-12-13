package com.egoriku.grodnoroads.map.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView

@Composable
fun KeepScreenOn(enabled: Boolean = true) {
    val currentView = LocalView.current

    DisposableEffect(enabled) {
        currentView.keepScreenOn = enabled
        onDispose {
            currentView.keepScreenOn = false
        }
    }
}