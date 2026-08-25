package com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Add
import com.egoriku.grodnoroads.foundation.icons.outlined.Remove
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButtonGroup
import com.egoriku.grodnoroads.foundation.uikit.button.ActionIcon
import com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.common.UIKitDemoContainer

@Composable
internal fun DemoActionButtonGroup(modifier: Modifier = Modifier) {
    UIKitDemoContainer(modifier = modifier, name = "ActionButtonGroup") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButtonGroup {
                ActionIcon(imageVector = GrodnoRoads.Outlined.Add, onClick = {})
                ActionIcon(imageVector = GrodnoRoads.Outlined.Remove, onClick = {})
            }
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoPrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoActionButtonGroup()
}
