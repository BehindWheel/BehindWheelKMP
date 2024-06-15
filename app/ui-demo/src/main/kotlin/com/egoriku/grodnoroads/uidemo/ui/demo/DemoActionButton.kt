package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_geo
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoActionButton() {
    UIDemoContainer(name = "ActionButton") {
        ActionButton(
            drawableResource = Res.drawable.ic_geo,
            onClick = { }
        )
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoPrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoActionButton()
}