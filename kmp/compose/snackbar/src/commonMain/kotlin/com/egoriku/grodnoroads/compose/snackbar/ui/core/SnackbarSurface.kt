package com.egoriku.grodnoroads.compose.snackbar.ui.core

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SnackbarSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.widthIn(max = 420.dp),
        color = MaterialTheme.colorScheme.inverseSurface,
        shape = MaterialTheme.shapes.medium,
        content = content
    )
}
