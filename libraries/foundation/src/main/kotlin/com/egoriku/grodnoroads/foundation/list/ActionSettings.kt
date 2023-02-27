package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionSettings(
    icon: ImageVector,
    text: String,
    trailing: @Composable (() -> Unit)
) {
    ListItem(
        modifier = Modifier.padding(horizontal = 8.dp),
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        text = {
            Text(text = text)
        },
        trailing = trailing
    )
    Divider()
}