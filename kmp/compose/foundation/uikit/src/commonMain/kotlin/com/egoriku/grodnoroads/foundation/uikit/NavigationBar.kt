package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
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
    Surface(
        modifier = modifier.clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
        color = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom))
                .defaultMinSize(minHeight = 56.dp)
                .selectableGroup(),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Immutable
data class NavigationBarItemColors(
    val selectedIconColor: Color,
    val selectedTextColor: Color,
    val unselectedIconColor: Color,
    val unselectedTextColor: Color
) {
    fun iconColor(selected: Boolean): Color = when {
        selected -> selectedIconColor
        else -> unselectedIconColor
    }

    fun textColor(selected: Boolean): Color = when {
        selected -> selectedTextColor
        else -> unselectedTextColor
    }
}

object NavigationBarItemDefaults {
    @Composable
    fun colors(
        selectedIconColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        selectedTextColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
        unselectedIconColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
        unselectedTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f)
    ) = NavigationBarItemColors(
        selectedIconColor = selectedIconColor,
        selectedTextColor = selectedTextColor,
        unselectedIconColor = unselectedIconColor,
        unselectedTextColor = unselectedTextColor
    )
}

@Composable
fun RowScope.NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val iconColor by animateColorAsState(
        targetValue = colors.iconColor(selected = selected),
        animationSpec = tween(durationMillis = 150),
        label = "NavBarItemIconColor"
    )
    val textColor by animateColorAsState(
        targetValue = colors.textColor(selected = selected),
        animationSpec = tween(durationMillis = 150),
        label = "NavBarItemTextColor"
    )

    Box(
        modifier = modifier
            .defaultMinSize(minHeight = 56.dp)
            .weight(1f)
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.Tab,
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CompositionLocalProvider(LocalContentColor provides iconColor) {
                icon()
            }

            CompositionLocalProvider(LocalContentColor provides textColor) {
                ProvideTextStyle(MaterialTheme.typography.labelMedium) {
                    label()
                }
            }
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun NavigationBarPreview() = GrodnoRoadsM3ThemePreview {
    var selectedTab by rememberMutableState { 0 }

    Box(modifier = Modifier.background(Color.Blue.copy(alpha = 0.5f))) {
        NavigationBar {
            NavigationBarItem(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                icon = {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        imageVector = GrodnoRoads.Outlined.Map,
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = "Map")
                }
            )
            NavigationBarItem(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                icon = {
                    Icon(
                        imageVector = GrodnoRoads.Outlined.Settings,
                        contentDescription = null
                    )
                },
                label = {
                    Text("Settings")
                }
            )
        }
    }
}
