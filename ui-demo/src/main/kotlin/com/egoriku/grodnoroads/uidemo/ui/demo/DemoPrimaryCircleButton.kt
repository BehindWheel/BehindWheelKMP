package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoPrimaryCircleButton() {
    UIDemoContainer(name = "PrimaryCircleButton") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PrimaryCircleButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null
                )
            }
            PrimaryCircleButton(onClick = { }, enabled = false) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoPrimaryCircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoPrimaryCircleButton()
}