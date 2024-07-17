package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Geo
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoActionButton() {
    UIDemoContainer(name = "ActionButton") {
        ActionButton(
            imageVector = GrodnoRoads.Outlined.Geo,
            onClick = { }
        )
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoPrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoActionButton()
}