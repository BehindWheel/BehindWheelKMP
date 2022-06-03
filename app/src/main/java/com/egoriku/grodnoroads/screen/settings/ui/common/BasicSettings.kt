package com.egoriku.grodnoroads.screen.settings.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BasicSettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp, start = 3.dp)
        )
        Card(modifier = Modifier.fillMaxWidth(), elevation = 3.dp) {
            content()
        }
    }
}