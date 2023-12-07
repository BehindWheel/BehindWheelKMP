package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.checkbox.Checkbox
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoCheckbox() {
    UIDemoContainer(name = "Checkbox") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val (isChecked, onCheckedChange) = rememberMutableState { true }

            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = true,
                onCheckedChange = {}
            )
            Checkbox(
                checked = true,
                enabled = false,
                onCheckedChange = {}
            )
            Checkbox(
                checked = false,
                onCheckedChange = {}
            )
            Checkbox(
                checked = false,
                enabled = false,
                onCheckedChange = {}
            )
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoCheckboxPreview() = GrodnoRoadsM3ThemePreview {
    DemoCheckbox()
}
