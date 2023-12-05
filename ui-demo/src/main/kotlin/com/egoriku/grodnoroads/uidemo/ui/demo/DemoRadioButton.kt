package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
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
        }
    }
}

private enum class Option {
    F, S
}