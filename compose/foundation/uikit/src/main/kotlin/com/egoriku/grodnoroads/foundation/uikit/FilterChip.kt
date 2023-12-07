package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Composable
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    androidx.compose.material3.FilterChip(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        onClick = onClick,
        label = label,
        border = FilterChipDefaults.filterChipBorder(
            selectedBorderWidth = 1.dp,
            selectedBorderColor = MaterialTheme.colorScheme.outline,
            borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
            selected = selected,
            enabled = true
        ),
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
private fun FilterChipPreview() = GrodnoRoadsM3ThemePreview {
    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = true,
            onClick = {},
            label = { Text("Option 1") }
        )
        FilterChip(
            selected = false,
            onClick = {},
            label = { Text("Option 1") }
        )
    }
}