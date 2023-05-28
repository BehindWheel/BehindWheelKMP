package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionSettings(
    icon: ImageVector,
    text: String,
    trailing: @Composable (() -> Unit)
) {
    ListItem(
        modifier = Modifier.padding(horizontal = 8.dp),
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = text)
        },
        trailingContent = trailing
    )
    Divider()
}