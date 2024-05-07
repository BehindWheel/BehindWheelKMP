package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButton
import com.egoriku.grodnoroads.shared.resources.MR
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoActionButton() {
    UIDemoContainer(name = "ActionButton") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ActionButton(
                icon = MR.images.ic_geo.drawableResId,
                onClick = { }
            )
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoPrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoActionButton()
}