package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.ArrowLeft
import com.egoriku.grodnoroads.foundation.icons.outlined.Telegram
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryCircleButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoSecondaryCircleButton(modifier: Modifier = Modifier) {
    UIDemoContainer(modifier = modifier, name = "SecondaryCircleButton") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryCircleButton(onClick = { }) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Telegram,
                    contentDescription = null
                )
            }
            SecondaryCircleButton(
                onClick = { },
                enabled = false
            ) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.ArrowLeft,
                    contentDescription = null
                )
            }
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoSecondaryCircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoSecondaryCircleButton()
}
