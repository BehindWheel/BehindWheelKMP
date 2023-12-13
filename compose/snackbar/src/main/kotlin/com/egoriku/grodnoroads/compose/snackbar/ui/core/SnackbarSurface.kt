package com.egoriku.grodnoroads.compose.snackbar.ui.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
internal fun SnackbarSurface(content: @Composable () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.inverseSurface,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
        shape = MaterialTheme.shapes.medium,
        content = content
    )
}