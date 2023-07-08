package com.egoriku.grodnoroads.screen.main

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.map.MapScreen
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.setting.screen.SettingsScreen

@Composable
fun MainUi(component: MainComponent) {
    val bottomNavItems = listOf(Screen.Map, Screen.Settings)
    val childStack by component.childStack.subscribeAsState()

    // TODO: Migrate to windowSizeClass
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            HorizontalOrientationLayout(bottomNavItems, childStack, component)
        }

        else -> {
            VerticalOrientationLayout(childStack, bottomNavItems, component)
        }
    }
}

@Composable
private fun VerticalOrientationLayout(
    childStack: ChildStack<*, Child>,
    bottomNavItems: List<Screen>,
    component: MainComponent
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Children(
            modifier = Modifier.weight(1f),
            stack = childStack,
        ) { created ->
            when (val child = created.instance) {
                is Child.Map -> MapScreen(component = child.component)
                is Child.Settings -> SettingsScreen(settingsComponent = child.component)
            }
        }

        NavigationBar(tonalElevation = 1.dp) {
            bottomNavItems.forEach { screen ->
                NavigationBarItem(
                    selected = screen.index == childStack.active.instance.index,
                    onClick = { component.onSelectTab(index = screen.index) },
                    icon = {
                        Icon(
                            painter = rememberVectorPainter(image = screen.icon),
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
    bottomNavItems: List<Screen>,
    childStack: ChildStack<*, Child>,
    component: MainComponent
) {
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail {
            Spacer(modifier = Modifier.weight(1f))
            bottomNavItems.forEach { screen ->
                NavigationRailItem(
                    selected = screen.index == childStack.active.instance.index,
                    onClick = { component.onSelectTab(index = screen.index) },
                    icon = {
                        Icon(
                            painter = rememberVectorPainter(image = screen.icon),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = stringResource(id = screen.labelId))
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        Children(
            modifier = Modifier.weight(1f),
            stack = childStack,
        ) { created ->
            when (val child = created.instance) {
                is Child.Map -> MapScreen(component = child.component)
                is Child.Settings -> SettingsScreen(settingsComponent = child.component)
            }
        }
    }
}