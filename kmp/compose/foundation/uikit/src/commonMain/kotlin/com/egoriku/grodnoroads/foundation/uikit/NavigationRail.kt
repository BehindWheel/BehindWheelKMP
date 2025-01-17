package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Map
import com.egoriku.grodnoroads.foundation.icons.outlined.Settings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight

@Composable
fun NavigationRail(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.material3.NavigationRail(
        modifier = modifier,
        content = content
    )
}

@Composable
fun NavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    NavigationRailItem(
        modifier = modifier,
        colors = NavigationRailItemDefaults.colors(
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.45f
            ),
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.45f
            )
        ),
        selected = selected,
        onClick = onClick,
        icon = icon,
        enabled = enabled
    )
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun RadioButtonPreview() = GrodnoRoadsM3ThemePreview {
    NavigationRail(modifier = Modifier.height(200.dp)) {
        NavigationRailItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Map,
                    contentDescription = null
                )
            }
        )
        NavigationRailItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Settings,
                    contentDescription = null
                )
            }
        )
    }
}
