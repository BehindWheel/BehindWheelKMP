package com.egoriku.grodnoroads.compose.snackbar

import androidx.compose.runtime.Stable

@Stable
interface SnackbarVisuals {
    val title: String
    val description: String?
    val actionLabel: String?
    val withDismissAction: Boolean
    val duration: SnackbarDuration
}