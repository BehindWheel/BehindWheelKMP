package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.NavigationBar
import com.egoriku.grodnoroads.foundation.uikit.NavigationBarItem
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoNavigationBar() {
    UIDemoContainer(name = "NavigationBar") {
        val elements by rememberMutableState { listOf(NavElement.Map, NavElement.Settings) }
        var selected by rememberMutableState { NavElement.Map }

        NavigationBar {
            elements.forEach {
                NavigationBarItem(
                    selected = selected == it,
                    onClick = { selected = it },
                    icon = {
                        Icon(
                            imageVector = when (it) {
                                NavElement.Map -> Icons.Default.Map
                                NavElement.Settings -> Icons.Default.Settings
                            },
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = when (it) {
                                NavElement.Map -> "Map"
                                NavElement.Settings -> "Settings"
                            }
                        )
                    }
                )
            }
        }
    }
}

private enum class NavElement {
    Map,
    Settings
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoNavigationBarPreview() = GrodnoRoadsM3ThemePreview {
    DemoNavigationBar()
}
