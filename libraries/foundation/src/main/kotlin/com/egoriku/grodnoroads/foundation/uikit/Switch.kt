package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview

@Composable
fun Switch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    val icon: (@Composable () -> Unit)? = if (isChecked) {
        {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }
    Switch(
        modifier = Modifier.scale(0.9f),
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
            checkedTrackColor = MaterialTheme.colorScheme.secondary
        ),
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        thumbContent = icon
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewSwitch() = GrodnoRoadsM3ThemePreview {
    Column {
        Switch(
            isChecked = true,
            onCheckedChange = {}
        )
        Switch(
            isChecked = false,
            onCheckedChange = {}
        )
    }
}