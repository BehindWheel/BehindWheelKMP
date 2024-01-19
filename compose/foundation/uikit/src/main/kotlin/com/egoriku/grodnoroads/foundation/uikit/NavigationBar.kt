package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.tonalElevation
import androidx.compose.material3.NavigationBarItem as Material3NavigationBarItem

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val tonalElevation = MaterialTheme.tonalElevation
    val containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(tonalElevation)

    NavigationBar(
        modifier = modifier.clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
        containerColor = containerColor,
        tonalElevation = tonalElevation,
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
    val tonalElevation = MaterialTheme.tonalElevation

    Material3NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            // https://developer.android.com/jetpack/androidx/releases/compose-material3#1.2.0-beta02:~:text=contains%20these%20commits.-,Known%20Bug,-A%20bug%20in
            selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
            indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(tonalElevation)
        ),
        selected = selected,
        onClick = onClick,
        icon = icon,
        label = label,
        enabled = enabled
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun NavigationBarPreview() = GrodnoRoadsM3ThemePreview {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text("Account")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null
                )
            },
            label = {
                Text("Build")
            }
        )
    }
}