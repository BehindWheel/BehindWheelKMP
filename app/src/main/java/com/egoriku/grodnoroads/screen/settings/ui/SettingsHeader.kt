package com.egoriku.grodnoroads.screen.settings.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsHeader(title: String) {
    Text(
        modifier = Modifier.padding(start = 16.dp, top = 32.dp, bottom = 8.dp),
        style = MaterialTheme.typography.h6,
        text = title,
    )
}