package com.egoriku.grodnoroads.foundation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.settings.common.SettingsTileIcon
import com.egoriku.grodnoroads.foundation.settings.common.SettingsTileText
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun SettingsWithValue(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    value: String,
    onClick: (currentValue: String) -> Unit,
) {
    Surface {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick(value) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SettingsTileIcon(icon = icon)
            SettingsTileText(title)
            Row(
                modifier = Modifier.padding(end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = value, color = MaterialTheme.colors.secondary)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSettingsMoreAction() {
    GrodnoRoadsTheme {
        SettingsWithValue(
            title = {
                Text(text = "Theme")
            },
            icon = {
                Image(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )
            },
            value = "Dark",
            onClick = {}
        )
    }
}