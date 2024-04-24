package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
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
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.NavigationRail
import com.egoriku.grodnoroads.foundation.uikit.NavigationRailItem
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoNavigationRail() {
    UIDemoContainer(name = "NavigationRail") {
        Row {
            val elements by rememberMutableState {
                listOf(
                    RailElement.Map to Icons.Default.Map,
                    RailElement.Settings to Icons.Default.Settings
                )
            }
            var selected by rememberMutableState { RailElement.Map }

            NavigationRail(modifier = Modifier.height(IntrinsicSize.Min)) {
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
            WeightSpacer()
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                NavigationRailItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                )
                NavigationRailItem(
                    selected = true,
                    enabled = false,
                    onClick = { },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                NavigationRailItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                )
                NavigationRailItem(
                    selected = false,
                    enabled = false,
                    onClick = { },
                    icon = {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                    }
                )
            }
        }
    }
}

private enum class RailElement {
    Map,
    Settings
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoNavigationRailPreview() = GrodnoRoadsM3ThemePreview {
    DemoNavigationRail()
}
