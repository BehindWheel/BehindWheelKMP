package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.NavigationBar
import com.egoriku.grodnoroads.foundation.uikit.NavigationBarItem
import com.egoriku.grodnoroads.shared.resources.MR
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer
import dev.icerock.moko.resources.compose.painterResource

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
                            painter = when (it) {
                                NavElement.Map -> painterResource(MR.images.ic_map)
                                NavElement.Settings -> painterResource(MR.images.ic_settings)
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
