package com.egoriku.grodnoroads.settings.debugtools.ui.uikit.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer
import com.egoriku.grodnoroads.foundation.uikit.dynamic.DynamicRadioButton
import com.egoriku.grodnoroads.settings.debugtools.ui.uikit.common.UIKitDemoContainer

@Composable
internal fun DemoRadioButton(modifier: Modifier = Modifier) {
    UIKitDemoContainer(modifier = modifier, name = "RadioButton") {
        var selectedOption by rememberMutableState { Option.F }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DynamicRadioButton(
                selected = selectedOption == Option.F,
                onClick = { selectedOption = Option.F }
            )
            DynamicRadioButton(
                selected = selectedOption == Option.S,
                onClick = { selectedOption = Option.S }
            )
            WeightSpacer()
            DynamicRadioButton(
                selected = true,
                onClick = {}
            )
            DynamicRadioButton(
                selected = true,
                enabled = false,
                onClick = {}
            )
            DynamicRadioButton(
                selected = false,
                enabled = true,
                onClick = {}
            )
            DynamicRadioButton(
                selected = false,
                enabled = false,
                onClick = {}
            )
        }
    }
}

private enum class Option {
    F,
    S
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoRadioButtonPreview() = GrodnoRoadsM3ThemePreview {
    DemoRadioButton()
}
