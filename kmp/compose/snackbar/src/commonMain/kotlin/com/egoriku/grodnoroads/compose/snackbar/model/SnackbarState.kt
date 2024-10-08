package com.egoriku.grodnoroads.compose.snackbar.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.suspendCancellableCoroutine

@Stable
class SnackbarState {

    var currentSnackbarData by mutableStateOf<SnackbarData?>(null)
        private set

    suspend fun show(message: SnackbarMessage) {
        currentSnackbarData = null
        try {
            suspendCancellableCoroutine { continuation ->
                currentSnackbarData = SnackbarDataImpl(continuation, message)
            }
        } finally {
            currentSnackbarData = null
        }
    }
}
