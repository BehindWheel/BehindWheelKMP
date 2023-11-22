package com.egoriku.grodnoroads.compose.snackbar.ui.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.foundation.theme.tonalElevation

@Composable
internal fun SnackbarSurface(content: @Composable () -> Unit) {
    Surface(
        color = when {
            MaterialTheme.colorScheme.isLight -> MaterialTheme.colorScheme.tertiaryContainer
            else -> MaterialTheme.colorScheme.surface
        },
        tonalElevation = MaterialTheme.tonalElevation,
        shape = MaterialTheme.shapes.medium,
        content = content
    )
}