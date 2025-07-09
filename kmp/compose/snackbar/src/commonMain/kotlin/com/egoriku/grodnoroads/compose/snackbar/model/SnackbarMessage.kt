package com.egoriku.grodnoroads.compose.snackbar.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.jvm.JvmInline
import org.jetbrains.compose.resources.StringResource

sealed interface MessageData {
    @JvmInline
    value class StringRes(val resource: StringResource) : MessageData

    @JvmInline
    value class Raw(val text: String) : MessageData
}

sealed interface SnackbarMessage {
    val title: MessageData
    val description: MessageData?
    val duration: SnackbarDuration

    data class SimpleMessage(
        override val title: MessageData,
        override val description: MessageData? = null,
        override val duration: SnackbarDuration = SnackbarDuration.Short,
        val imageVector: ImageVector? = null,
        val tint: Color? = null
    ) : SnackbarMessage

    data class ActionMessage(
        override val title: MessageData,
        override val description: MessageData? = null,
        override val duration: SnackbarDuration = SnackbarDuration.Indefinite,
        val onAction: () -> Unit
    ) : SnackbarMessage
}

enum class SnackbarDuration {
    Short,
    Long,
    Indefinite
}
