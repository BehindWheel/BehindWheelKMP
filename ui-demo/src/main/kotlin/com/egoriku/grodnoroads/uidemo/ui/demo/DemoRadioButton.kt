package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.RadioButton
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoRadioButton() {
    UIDemoContainer(name = "RadioButton") {
        var selectedOption by rememberMutableState { Option.F }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            RadioButton(
                selected = selectedOption == Option.F,
                onClick = { selectedOption = Option.F }
            )
            RadioButton(
                selected = selectedOption == Option.S,
                onClick = { selectedOption = Option.S }
            )
            Spacer(modifier = Modifier.weight(1f))
            RadioButton(
                selected = true,
                onClick = {},
            )
            RadioButton(
                selected = true,
                enabled = false,
                onClick = {},
            )
            RadioButton(
                selected = false,
                enabled = true,
                onClick = {},
            )
            RadioButton(
                selected = false,
                enabled = false,
                onClick = {},
            )
        }
    }
}

private enum class Option {
    F, S
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoRadioButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoRadioButton()
}