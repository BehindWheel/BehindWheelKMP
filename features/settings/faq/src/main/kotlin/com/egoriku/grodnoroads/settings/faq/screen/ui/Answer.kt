package com.egoriku.grodnoroads.settings.faq.screen.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Answer(text: String) {
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = text,
        style = MaterialTheme.typography.subtitle1
    )
}