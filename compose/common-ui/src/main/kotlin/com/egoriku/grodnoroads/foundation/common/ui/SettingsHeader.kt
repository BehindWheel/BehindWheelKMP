package com.egoriku.grodnoroads.foundation.common.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsHeader(
    title: String,
    paddingValues: PaddingValues = PaddingValues(start = 24.dp, top = 16.dp, bottom = 4.dp)
) {
    Text(
        modifier = Modifier.padding(paddingValues),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        text = title,
    )
}