package com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.common.UIKitDemoContainer

@Composable
internal fun DemoPrimaryButton(modifier: Modifier = Modifier) {
    UIKitDemoContainer(modifier = modifier, name = "PrimaryButton") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = "Enabled state",
                onClick = { }
            )
            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = "Disabled state",
                onClick = { },
                enabled = false
            )
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoPrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoPrimaryButton()
}
