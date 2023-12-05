package com.egoriku.grodnoroads.foundation.uikit.checkbox

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview

@Composable
fun Checkbox(
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit
) {
    androidx.compose.material3.Checkbox(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun CheckboxPreview() = GrodnoRoadsM3ThemePreview {
    Row(modifier = Modifier.padding(16.dp)) {
        Checkbox(checked = true, onCheckedChange = {})
        Checkbox(checked = false, onCheckedChange = {})
    }
}