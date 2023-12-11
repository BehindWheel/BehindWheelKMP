package com.egoriku.grodnoroads.foundation.common.ui.list

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ActionSettings(
    iconResId: Int,
    text: String,
    trailing: @Composable (() -> Unit)
) {
    ListItem(
        modifier = Modifier
            .height(48.dp)
            .padding(start = 8.dp),
        leadingContent = {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = text)
        },
        trailingContent = trailing
    )
}