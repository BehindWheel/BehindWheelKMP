package com.egoriku.grodnoroads.foundation.common.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview

@Composable
fun ClickableSettingsItem(
    imageVector: ImageVector,
    text: String,
    paddingValues: PaddingValues = PaddingValues(start = 8.dp),
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .height(48.dp)
            .clickable(
                role = Role.Button,
                onClick = onClick,
            )
            .padding(paddingValues),
        leadingContent = {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = text)
        },
    )
}

@GrodnoRoadsPreview
@Composable
private fun ClickableSettingsItemPreview() = GrodnoRoadsM3ThemePreview {
    ClickableSettingsItem(
        imageVector = Icons.Default.PlayCircle,
        text = "Test test test",
        onClick = {}
    )
}