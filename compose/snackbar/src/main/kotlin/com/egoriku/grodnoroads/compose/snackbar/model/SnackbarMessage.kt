package com.egoriku.grodnoroads.compose.snackbar.model

sealed interface MessageData {
    @JvmInline
    value class Resource(val id: Int) : MessageData

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
        override val duration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackbarMessage

    data class ActionMessage(
        override val title: MessageData,
        override val description: MessageData? = null,
        override val duration: SnackbarDuration = SnackbarDuration.Indefinite,
        val onAction: () -> Unit,
    ) : SnackbarMessage
}

enum class SnackbarDuration {
    Short,
    Long,
    Indefinite
}