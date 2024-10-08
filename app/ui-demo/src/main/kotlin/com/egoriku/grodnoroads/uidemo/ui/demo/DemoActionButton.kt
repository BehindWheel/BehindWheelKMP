package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Geo
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoActionButton(modifier: Modifier = Modifier) {
    UIDemoContainer(modifier = modifier, name = "ActionButton") {
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
