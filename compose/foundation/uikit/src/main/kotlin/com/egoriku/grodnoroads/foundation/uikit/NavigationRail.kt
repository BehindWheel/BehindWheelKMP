package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.tonalElevation

@Composable
fun NavigationRail(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    val tonalElevation = MaterialTheme.tonalElevation
    val containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(tonalElevation)

    androidx.compose.material3.NavigationRail(
        modifier = modifier,
        containerColor = containerColor,
        content = content
    )
}

@Composable
fun NavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    enabled: Boolean = true
) {
    val tonalElevation = MaterialTheme.tonalElevation
    val containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(tonalElevation)

    androidx.compose.material3.NavigationRailItem(
        colors = NavigationRailItemDefaults.colors(
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.45f
            ),
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.45f
            ),
            indicatorColor = containerColor,
        ),
        selected = selected,
        onClick = onClick,
        icon = icon,
        enabled = enabled
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun RadioButtonPreview() = GrodnoRoadsM3ThemePreview {
    NavigationRail(modifier = Modifier.height(200.dp)) {
        NavigationRailItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            }
        )
        NavigationRailItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null
                )
            }
        )
    }
}