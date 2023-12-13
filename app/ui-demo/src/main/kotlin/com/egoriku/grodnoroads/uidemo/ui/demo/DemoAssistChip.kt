package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.AssistChip
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoAssistChip() {
    UIDemoContainer(name = "AssistChip") {
        Column {
            Row {
                var isSelected by rememberMutableState { true }

                AssistChip(
                    selected = isSelected,
                    onClick = { isSelected = !isSelected },
                    label = {
                        Text("Assist Chip")
                    }
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    selected = true,
                    onClick = {},
                    label = {
                        Text("Chip")
                    }
                )
                AssistChip(
                    selected = true,
                    enabled = false,
                    onClick = {},
                    label = {
                        Text("Chip")
                    }
                )
                AssistChip(
                    selected = false,
                    onClick = {},
                    label = {
                        Text("Chip")
                    }
                )
                AssistChip(
                    selected = false,
                    enabled = false,
                    onClick = {},
                    label = {
                        Text("Chip")
                    }
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoAssistChipPreview() = GrodnoRoadsM3ThemePreview {
    DemoAssistChip()
}
