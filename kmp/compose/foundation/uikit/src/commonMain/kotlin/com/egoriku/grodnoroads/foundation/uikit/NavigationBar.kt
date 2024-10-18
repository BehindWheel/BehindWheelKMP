package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem as Material3NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Map
import com.egoriku.grodnoroads.foundation.icons.outlined.Settings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    NavigationBar(
        modifier = modifier.clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
        content = content
    )
}

@Composable
fun RowScope.NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    enabled: Boolean = true
) {
    Material3NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f)
        ),
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = label,
        enabled = enabled
    )
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun NavigationBarPreview() = GrodnoRoadsM3ThemePreview {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Map,
                    contentDescription = null
                )
            },
            label = {
                Text(text = "Account")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Settings,
                    contentDescription = null
                )
            },
            label = {
                Text("Build")
            }
        )
    }
}
