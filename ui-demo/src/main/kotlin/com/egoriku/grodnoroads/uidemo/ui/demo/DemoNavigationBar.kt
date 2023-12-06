package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.NavigationBar
import com.egoriku.grodnoroads.foundation.uikit.NavigationBarItem
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoNavigationBar() {
    UIDemoContainer(name = "NavigationBar") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val elements by rememberMutableState {
                listOf(
                    NavElement.Map to Icons.Default.Map,
                    NavElement.Settings to Icons.Default.Settings
                )
            }
            var selected by rememberMutableState { NavElement.Map }

            NavigationBar {
                elements.forEach {
                    NavigationBarItem(
                        selected = selected == it.first,
                        onClick = { selected = it.first },
                        icon = {
                            Icon(imageVector = it.second, contentDescription = null)
                        }
                    )
                }
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
