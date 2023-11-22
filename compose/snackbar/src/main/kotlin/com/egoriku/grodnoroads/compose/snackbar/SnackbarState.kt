package com.egoriku.grodnoroads.compose.snackbar

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Stable
class SnackbarState {

    var currentSnackbarData by mutableStateOf<SnackbarData?>(null)
        private set

    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        description: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = when (actionLabel) {
            null -> SnackbarDuration.Short
            else -> SnackbarDuration.Indefinite
        }
    ): SnackbarResult = showSnackbar(
        SnackbarVisualsImpl(
            title = message,
            actionLabel = actionLabel,
            withDismissAction = withDismissAction,
            duration = duration,
            description = description
        )
    )

    fun dismissSnackBar() {
        currentSnackbarData = null
    }

    suspend fun showSnackbar(visuals: SnackbarVisuals): SnackbarResult {
        currentSnackbarData = null
        try {
            return suspendCancellableCoroutine { continuation ->
                currentSnackbarData = SnackbarDataImpl(visuals, continuation)
            }
        } finally {
            currentSnackbarData = null
        }
    }

    private data class SnackbarVisualsImpl(
        override val title: String,
        override val actionLabel: String?,
        override val withDismissAction: Boolean,
        override val duration: SnackbarDuration,
        override val description: String?
    ) : SnackbarVisuals

    private data class SnackbarDataImpl(
        override val visuals: SnackbarVisuals,
        private val continuation: CancellableContinuation<SnackbarResult>
    ) : SnackbarData {

        override fun performAction() {
            if (continuation.isActive) continuation.resume(SnackbarResult.ActionPerformed)
        }

        override fun dismiss() {
            if (continuation.isActive) continuation.resume(SnackbarResult.Dismissed)
        }
    }
}