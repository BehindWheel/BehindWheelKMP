package com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Geo
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButton
import com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.common.UIKitDemoContainer

@Composable
internal fun DemoActionButton(modifier: Modifier = Modifier) {
    UIKitDemoContainer(modifier = modifier, name = "ActionButton") {
        ActionButton(
            imageVector = GrodnoRoads.Outlined.Geo,
            onClick = { }
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoPrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoActionButton()
}
