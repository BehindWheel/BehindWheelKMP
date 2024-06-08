package com.egoriku.grodnoroads.compose.snackbar.tool

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.extensions.toast

actual class ToastHelper(private val context: Context) {
    actual fun show(string: String) {
        context.toast(string)
    }
}

@Composable
actual fun rememberToastHelper(): ToastHelper {
    val context = LocalContext.current
    return remember { ToastHelper(context) }
}