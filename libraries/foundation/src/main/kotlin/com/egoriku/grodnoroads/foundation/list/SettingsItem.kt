package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingsItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .clickable(onClick = onClick)
            .height(48.dp)
            .padding(start = 8.dp),
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = text)
        },
        trailingContent = {
            Icon(
                Icons.Filled.NavigateNext,
                contentDescription = null
            )
        }
    )
}