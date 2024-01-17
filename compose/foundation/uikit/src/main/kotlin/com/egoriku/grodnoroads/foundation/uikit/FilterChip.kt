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
fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    FilterChip(
        modifier = modifier,
        enabled = enabled,
        selected = selected,
        onClick = onClick,
        label = label,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.inverseSurface,
            selectedLabelColor = MaterialTheme.colorScheme.inversePrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.inversePrimary
        ),
        border = FilterChipDefaults.filterChipBorder(
            selectedBorderColor = MaterialTheme.colorScheme.outline,
            borderColor = MaterialTheme.colorScheme.outline,
            selected = selected,
            enabled = enabled
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