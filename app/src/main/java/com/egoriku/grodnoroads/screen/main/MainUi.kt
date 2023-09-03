package com.egoriku.grodnoroads.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Expanded
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.map.MapScreen
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.setting.screen.SettingsScreen
import com.egoriku.grodnoroads.util.LocalWindowSizeClass

private val NavigationBarHeight : Dp = 80.dp

@Composable
fun MainUi(component: MainComponent) {
    val windowSizeClass = LocalWindowSizeClass.current

    val childStack by component.childStack.subscribeAsState()

    when (windowSizeClass.widthSizeClass) {
        Expanded -> {
            HorizontalOrientationLayout(
                childStack = childStack,
                component = component
            )
        }

        else -> {
            VerticalOrientationLayout(
                childStack = childStack,
                component = component
            )
        }
    }
}

@Composable
private fun VerticalOrientationLayout(
    childStack: ChildStack<*, Child>,
    component: MainComponent
) {
    val bottomNavItems = remember { listOf(Screen.Map, Screen.Settings) }

    val contentPaddingValues = WindowInsets
        .navigationBars
        .add(WindowInsets(bottom = NavigationBarHeight))
        .asPaddingValues()

    Box(modifier = Modifier.fillMaxSize()) {
        Children(
            modifier = Modifier.fillMaxSize(1f),
            stack = childStack,
        ) { created ->
            when (val child = created.instance) {
                is Child.Map -> {
                    MapScreen(
                        contentPadding = contentPaddingValues,
                        component = child.component
                    )
                }

                is Child.Settings -> SettingsScreen(
                    contentPadding = contentPaddingValues,
                    settingsComponent = child.component
                )
            }
        }

        val tonalElevation = if (MaterialTheme.colorScheme.isLight) 0.dp else 6.dp
        NavigationBar(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
            tonalElevation = tonalElevation
        ) {
            bottomNavItems.forEach { screen ->
                NavigationBarItem(
                    selected = screen.index == childStack.active.instance.index,
                    onClick = { component.onSelectTab(index = screen.index) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
                        indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                            tonalElevation
                        )
                    ),
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.iconRes),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(id = screen.labelId))
                    }
                )
            }
        }
    }
}

@Composable
private fun HorizontalOrientationLayout(
    childStack: ChildStack<*, Child>,
    component: MainComponent
) {
    val bottomNavItems = remember { listOf(Screen.Map, Screen.Settings) }

    Row(modifier = Modifier.fillMaxSize()) {
        val tonalElevation = if (MaterialTheme.colorScheme.isLight) 0.dp else 6.dp
        val containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(tonalElevation)

        NavigationRail(containerColor = containerColor) {
            bottomNavItems.forEach { screen ->
                NavigationRailItem(
                    colors = NavigationRailItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.45f),
                        indicatorColor = containerColor
                    ),
                    selected = screen.index == childStack.active.instance.index,
                    onClick = { component.onSelectTab(index = screen.index) },
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.iconRes),
                            contentDescription = null
                        )
                    }
                )
            }
        }
        Children(
            modifier = Modifier.weight(1f),
            stack = childStack,
        ) { created ->
            when (val child = created.instance) {
                is Child.Map -> MapScreen(
                    contentPadding = PaddingValues(),
                    component = child.component
                )

                is Child.Settings -> SettingsScreen(
                    contentPadding = PaddingValues(),
                    settingsComponent = child.component
                )
            }
        }
    }
}