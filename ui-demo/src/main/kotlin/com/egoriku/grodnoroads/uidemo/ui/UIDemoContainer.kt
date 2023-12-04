package com.egoriku.grodnoroads.uidemo.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun UIDemoContainer(name: String, content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .border(0.5.dp, Color.Gray)
            .padding(16.dp)
    ) {
        Text(text = name, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        content()
    }
}