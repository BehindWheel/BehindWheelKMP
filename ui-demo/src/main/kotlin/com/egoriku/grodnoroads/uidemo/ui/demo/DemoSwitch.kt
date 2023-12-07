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
import com.egoriku.grodnoroads.foundation.uikit.Switch
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoSwitch() {
    UIDemoContainer(name = "Switch") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val (isChecked, onCheckedChange) = rememberMutableState { true }
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = true,
                onCheckedChange = {}
            )
            Switch(
                checked = true,
                enabled = false,
                onCheckedChange = {}
            )
            Switch(
                checked = false,
                onCheckedChange = {}
            )
            Switch(
                checked = false,
                enabled = false,
                onCheckedChange = {}
            )
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DemoSwitchPreview() = GrodnoRoadsM3ThemePreview {
    DemoSwitch()
}