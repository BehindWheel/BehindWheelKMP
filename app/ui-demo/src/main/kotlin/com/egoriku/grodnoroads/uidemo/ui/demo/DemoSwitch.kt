package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.Switch
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoSwitch(modifier: Modifier = Modifier) {
    UIDemoContainer(modifier = modifier, name = "Switch") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val (isChecked, onCheckedChange) = rememberMutableState { true }
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
            WeightSpacer()
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

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoSwitchPreview() = GrodnoRoadsM3ThemePreview {
    DemoSwitch()
}
