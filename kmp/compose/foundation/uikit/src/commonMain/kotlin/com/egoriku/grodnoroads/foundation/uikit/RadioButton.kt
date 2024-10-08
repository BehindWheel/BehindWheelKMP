package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight

@Composable
fun RadioButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    androidx.compose.material3.RadioButton(
        modifier = modifier,
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.secondary
        ),
        enabled = enabled,
        selected = selected,
        onClick = onClick
    )
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun RadioButtonPreview() = GrodnoRoadsM3ThemePreview {
    Row(modifier = Modifier.padding(16.dp)) {
        RadioButton(selected = true, onClick = {})
        RadioButton(selected = false, onClick = {})
    }
}
