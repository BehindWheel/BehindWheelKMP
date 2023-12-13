package com.egoriku.grodnoroads.foundation.common.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun MoreActionSettings(
    icon: ImageVector,
    text: String,
    value: String,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = value)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                )
            }
        }
    )
}

@Composable
fun MoreActionSettings(
    iconResId: Int,
    text: String,
    value: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .clickable(onClick = onClick)
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
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = value)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                )
            }
        }
    )
}