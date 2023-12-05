package com.egoriku.grodnoroads.uidemo.ui.demo

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.uikit.checkbox.TriStateCheckbox
import com.egoriku.grodnoroads.uidemo.ui.UIDemoContainer

@Composable
fun DemoTriStateCheckbox() {
    UIDemoContainer(name = "TriStateCheckbox") {
        var state by rememberMutableState { ToggleableState.Off }

        fun onClick() {
            state = when (state) {
                ToggleableState.Off -> ToggleableState.Indeterminate
                ToggleableState.Indeterminate -> ToggleableState.On
                ToggleableState.On -> ToggleableState.Off
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TriStateCheckbox(state = ToggleableState.Off, onClick = {})
            TriStateCheckbox(state = ToggleableState.Indeterminate, onClick = {})
            TriStateCheckbox(state = ToggleableState.On, onClick = {})
            TriStateCheckbox(state = ToggleableState.On, enabled = false, onClick = {})
            Spacer(modifier = Modifier.weight(1f))
            TriStateCheckbox(state = state, onClick = ::onClick)
        }
    }
}