package com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.demo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.layout.WeightSpacer
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.dynamic.TriStateCheckbox
import com.egoriku.grodnoroads.settings.debugtools.screen.uikit.ui.common.UIKitDemoContainer

@Composable
internal fun DemoTriStateCheckbox(modifier: Modifier = Modifier) {
    UIKitDemoContainer(modifier = modifier, name = "TriStateCheckbox") {
        var state by rememberMutableState { ToggleableState.Off }

        fun onClick() {
            state = when (state) {
                ToggleableState.Off -> ToggleableState.Indeterminate
                ToggleableState.Indeterminate -> ToggleableState.On
                ToggleableState.On -> ToggleableState.Off
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TriStateCheckbox(state = state, onClick = ::onClick)
            WeightSpacer()
            TriStateCheckbox(state = ToggleableState.Off, onClick = {})
            TriStateCheckbox(state = ToggleableState.Indeterminate, onClick = {})
            TriStateCheckbox(state = ToggleableState.On, onClick = {})
            TriStateCheckbox(state = ToggleableState.On, enabled = false, onClick = {})
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DemoTriStateCheckboxPreview() = GrodnoRoadsM3ThemePreview {
    DemoTriStateCheckbox()
}
