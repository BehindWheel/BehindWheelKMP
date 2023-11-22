package com.egoriku.grodnoroads.compose.snackbar

import androidx.compose.runtime.Stable

@Stable
interface SnackbarData {
    val visuals: SnackbarVisuals

    fun performAction()
    fun dismiss()
}