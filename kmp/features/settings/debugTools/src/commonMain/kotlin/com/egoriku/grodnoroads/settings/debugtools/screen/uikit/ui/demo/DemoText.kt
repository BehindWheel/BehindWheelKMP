package com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.demo

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.common.UIKitDemoContainer

@Composable
internal fun DemoText(modifier: Modifier = Modifier) {
    UIKitDemoContainer(modifier = modifier, name = "Text") {
        Text(text = "Normal text")
        DisabledText(text = "Disabled text")
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoTextPreview() = GrodnoRoadsM3ThemePreview {
    DemoText()
}
