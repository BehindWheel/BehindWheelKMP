package com.egoriku.grodnoroads.guidance.screen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView

@Composable
actual fun KeepScreenOn(enabled: Boolean) {
    val currentView = LocalView.current

    DisposableEffect(enabled) {
        currentView.keepScreenOn = enabled
        onDispose {
            currentView.keepScreenOn = false
        }
    }
}
