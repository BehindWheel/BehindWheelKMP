package com.egoriku.grodnoroads.guidance.screen.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import platform.UIKit.UIApplication

@Composable
actual fun KeepScreenOn(enabled: Boolean) {
    DisposableEffect(enabled) {
        keepScreenOn = enabled

        onDispose {
            keepScreenOn = false
        }
    }
}

private var keepScreenOn: Boolean
    set(value) {
        UIApplication.sharedApplication.idleTimerDisabled = value
    }
    get() = UIApplication.sharedApplication.isIdleTimerDisabled()
