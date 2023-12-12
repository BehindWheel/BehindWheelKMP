package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.ClickableIntRange
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoClickableRange() {
    UIDemoContainer(name = "ClickableRange") {
        var value by rememberMutableState { 500 }

        ClickableIntRange(
            min = 200,
            max = 1000,
            step = 100,
            value = value,
            onLongClick = {},
            onValueChange = { value = it },
            formatter = { "$it meters" }
        )
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoSwitchPreview() = GrodnoRoadsM3ThemePreview {
    DemoClickableRange()
}