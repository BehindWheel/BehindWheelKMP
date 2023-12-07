package com.egoriku.grodnoroads.foundation.uikit.checkbox

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Composable
fun TriStateCheckbox(
    state: ToggleableState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    androidx.compose.material3.TriStateCheckbox(
        modifier = modifier,
        enabled = enabled,
        state = state,
        onClick = onClick,
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun TriStateCheckboxPreview() = GrodnoRoadsM3ThemePreview {
    Row(modifier = Modifier.padding(16.dp)) {
        Column {
            val (state, onStateChange) = rememberMutableState { true }
            val (state2, onStateChange2) = rememberMutableState { false }

            val parentState = remember(state, state2) {
                if (state && state2) ToggleableState.On
                else if (!state && !state2) ToggleableState.Off
                else ToggleableState.Indeterminate
            }
            val onParentClick = {
                val s = parentState != ToggleableState.On
                onStateChange(s)
                onStateChange2(s)
            }

            TriStateCheckbox(
                state = parentState,
                onClick = onParentClick,
            )
            Checkbox(
                modifier = Modifier.padding(start = 32.dp),
                checked = state,
                onCheckedChange = onStateChange
            )
            Checkbox(
                modifier = Modifier.padding(start = 32.dp),
                checked = state2,
                onCheckedChange = onStateChange2
            )
        }
    }
}