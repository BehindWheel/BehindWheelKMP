package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Composable
fun AssistChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    AssistChip(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        label = label,
        border = AssistChipDefaults.assistChipBorder(enabled = selected),
        leadingIcon = if (selected) {
            {
                Icon(
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                )
            }
        } else {
            null
        }
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun AssistChipPreview() = GrodnoRoadsM3ThemePreview {
    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AssistChip(
            selected = true,
            onClick = {},
            label = { Text("Option 1") }
        )
        AssistChip(
            selected = false,
            onClick = {},
            label = { Text("Option 1") }
        )
    }
}