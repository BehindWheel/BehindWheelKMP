package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.TextButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoTextButton(modifier: Modifier = Modifier) {
    UIDemoContainer(modifier = modifier, name = "TextButton") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(
                modifier = Modifier.weight(1f),
                text = "Enabled state",
                onClick = { }
            )
            TextButton(
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
private fun DemoTextButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoTextButton()
}
