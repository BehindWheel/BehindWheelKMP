package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.NavigationRail
import com.egoriku.grodnoroads.foundation.uikit.NavigationRailItem
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoNavigationRail() {
    UIDemoContainer(name = "NavigationRail") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val elements by rememberMutableState {
                listOf(
                    Element.Map to Icons.Default.Map,
                    Element.Settings to Icons.Default.Settings
                )
            }
            var selected by rememberMutableState { Element.Map }

            NavigationRail(modifier = Modifier.height(150.dp)) {
                elements.forEach {
                    NavigationRailItem(
                        selected = selected == it.first,
                        onClick = { selected = it.first },
                        icon = {
                            Icon(imageVector = it.second, contentDescription = null)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                NavigationRailItem(
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                )
                NavigationRailItem(
                    selected = true,
                    enabled = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                NavigationRailItem(
                    selected = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                )
                NavigationRailItem(
                    selected = false,
                    enabled = false,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                )
            }
        }
    }
}

private enum class Element {
    Map,
    Settings
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoNavigationRailPreview() = GrodnoRoadsM3ThemePreview {
    DemoNavigationRail()
}
