package com.egoriku.grodnoroads.compose.snackbar.tool

import androidx.compose.runtime.Composable

expect class ToastHelper {
    fun show(string: String)
}

@Composable
expect fun rememberToastHelper(): ToastHelper
