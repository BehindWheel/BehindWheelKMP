package com.egoriku.grodnoroads.compose.snackbar.tool

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

actual class ToastHelper {
    actual fun show(string: String) {
        // TODO: make similar to android toast
    }
}

@Composable
actual fun rememberToastHelper(): ToastHelper {
    return remember { ToastHelper() }
}
