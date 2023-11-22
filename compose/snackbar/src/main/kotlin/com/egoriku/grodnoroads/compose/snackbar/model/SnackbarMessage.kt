package com.egoriku.grodnoroads.compose.snackbar.model

sealed interface SnackbarMessage {
    val title: String
    val description: String?
    val duration: SnackbarDuration

    data class Simple(
        override val title: String,
        override val description: String?,
        override val duration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackbarMessage

    data class WithAction(
        override val title: String,
        override val description: String?,
        override val duration: SnackbarDuration = SnackbarDuration.Indefinite,
        val onAction: () -> Unit,
    ) : SnackbarMessage
}

enum class SnackbarDuration {
    Short,
    Long,
    Indefinite
}