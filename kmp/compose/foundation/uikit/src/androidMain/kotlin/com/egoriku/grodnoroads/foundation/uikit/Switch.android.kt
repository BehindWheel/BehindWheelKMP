package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Check
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Composable
actual fun Switch(
    checked: Boolean,
    modifier: Modifier,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val icon: (@Composable () -> Unit)? = if (checked) {
        {
            Icon(
                imageVector = GrodnoRoads.Outlined.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }
    androidx.compose.material3.Switch(
        modifier = modifier,
        checked = checked,
        enabled = enabled,
        onCheckedChange = onCheckedChange,
        thumbContent = icon
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun PreviewSwitch() = GrodnoRoadsM3ThemePreview {
    Column(modifier = Modifier.padding(16.dp)) {
        Switch(
            checked = true,
            onCheckedChange = {}
        )
        Switch(
            checked = false,
            onCheckedChange = {}
        )
    }
}