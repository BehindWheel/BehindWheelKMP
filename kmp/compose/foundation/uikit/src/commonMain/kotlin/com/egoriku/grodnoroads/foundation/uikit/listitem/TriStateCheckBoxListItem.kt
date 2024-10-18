package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.checkbox.TriStateCheckbox

@Composable
fun TriStateCheckBoxListItem(
    text: String,
    state: ToggleableState,
    modifier: Modifier = Modifier,
    onToggle: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .triStateToggleable(state = state, onClick = onToggle)
            .padding(start = 6.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TriStateCheckbox(
            state = state,
            onClick = onToggle
        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f),
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@PreviewGrodnoRoads
@Composable
private fun TriStateCheckBoxListItemPreview() = GrodnoRoadsM3ThemePreview {
    var state by rememberMutableState { ToggleableState.Off }

    TriStateCheckBoxListItem(
        text = "За рулем | Гродно",
        state = state,
        onToggle = {
            state = when (state) {
                ToggleableState.Indeterminate -> ToggleableState.Off
                ToggleableState.Off -> ToggleableState.On
                ToggleableState.On -> ToggleableState.Indeterminate
            }
        }
    )
}
