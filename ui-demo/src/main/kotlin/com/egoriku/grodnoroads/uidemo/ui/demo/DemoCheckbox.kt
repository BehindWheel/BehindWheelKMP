package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.checkbox.Checkbox
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoCheckbox() {
    UIDemoContainer(name = "Checkbox") {
        val (checkbox1, onChange1) = rememberMutableState { true }
        val (checkbox2, onChange2) = rememberMutableState { false }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Checkbox(
                checked = checkbox1,
                onCheckedChange = onChange1
            )
            Checkbox(
                checked = checkbox2,
                onCheckedChange = onChange2
            )
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoCheckboxPreview() = GrodnoRoadsM3ThemePreview {
    DemoCheckbox()
}
