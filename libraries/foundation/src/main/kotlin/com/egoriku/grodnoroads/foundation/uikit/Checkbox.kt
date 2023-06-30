package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview

@Composable
fun Checkbox(
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
) {
    Checkbox(
        modifier = modifier,
        checked = isChecked,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun TriStateCheckbox(
    state: ToggleableState,
    modifier: Modifier = Modifier,
    onCheckedChange: () -> Unit,
) {
    TriStateCheckbox(
        modifier = modifier,
        state = state,
        onClick = onCheckedChange
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewCheckbox() = GrodnoRoadsM3ThemePreview {
    Column(modifier = Modifier.fillMaxWidth()) {
        val (state, onStateChange) = remember { mutableStateOf(true) }
        val (state2, onStateChange2) = remember { mutableStateOf(true) }

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
            onCheckedChange = onParentClick
        )
        Column(modifier = Modifier.padding(start = 36.dp)) {
            Checkbox(
                isChecked = state,
                onCheckedChange = onStateChange
            )
            Checkbox(
                isChecked = state2,
                onCheckedChange = onStateChange2
            )
        }
    }
}