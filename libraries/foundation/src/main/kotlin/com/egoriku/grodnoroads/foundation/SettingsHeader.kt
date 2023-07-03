package com.egoriku.grodnoroads.foundation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SettingsHeader(
    title: String,
    start: Dp = 24.dp,
    top: Dp = 16.dp,
    bottom: Dp = 4.dp
) {
    Text(
        modifier = Modifier.padding(start = start, top = top, bottom = bottom),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        text = title,
    )
}