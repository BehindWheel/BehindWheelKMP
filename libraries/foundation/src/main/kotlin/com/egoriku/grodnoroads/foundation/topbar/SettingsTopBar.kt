package com.egoriku.grodnoroads.foundation.topbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme

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

@GrodnoRoadsPreview
@Composable
private fun SettingsTopBarPreview() = GrodnoRoadsTheme {
    SettingsTopBar(title = "Test", onBack = {})
}