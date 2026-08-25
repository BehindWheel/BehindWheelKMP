package com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.layout.WeightSpacer
import com.egoriku.grodnoroads.foundation.uikit.dynamic.Switch
import com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.common.UIKitDemoContainer

@Composable
internal fun DemoSwitch(modifier: Modifier = Modifier) {
    UIKitDemoContainer(modifier = modifier, name = "Switch") {
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
