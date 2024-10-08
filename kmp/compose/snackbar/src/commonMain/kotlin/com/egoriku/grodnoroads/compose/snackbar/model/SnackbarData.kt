package com.egoriku.grodnoroads.compose.snackbar.model

import androidx.compose.runtime.Stable
import kotlin.coroutines.resume
import kotlinx.coroutines.CancellableContinuation

@Stable
interface SnackbarData {
    val message: SnackbarMessage

    fun dismiss()
}

internal class SnackbarDataImpl(
    private val continuation: CancellableContinuation<Unit>,
    override val message: SnackbarMessage
) : SnackbarData {

    override fun dismiss() {
        if (continuation.isActive) continuation.resume(Unit)
    }
}
