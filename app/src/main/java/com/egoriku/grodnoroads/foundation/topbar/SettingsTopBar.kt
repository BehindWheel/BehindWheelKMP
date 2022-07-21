package com.egoriku.grodnoroads.foundation.topbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsTopBar(
    title: String,
    onBack: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
        },
        title = {
            Text(text = title)
        }
    )
}

@Preview
@Composable
private fun SettingsTopBarPreview() {
    SettingsTopBar(title = "Test") {

    }
}