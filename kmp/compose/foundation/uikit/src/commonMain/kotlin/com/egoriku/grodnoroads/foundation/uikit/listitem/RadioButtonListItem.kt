package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.HorizontalSpacer
import com.egoriku.grodnoroads.foundation.uikit.dynamic.RadioButton

@Composable
fun RadioButtonListItem(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                role = Role.RadioButton,
                onClick = onClick
            )
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f),
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalSpacer(12.dp)
    }
}

@PreviewGrodnoRoads
@Composable
private fun RadioButtonListItemPreview() = GrodnoRoadsM3ThemePreview {
    var state by rememberMutableState { true }

    RadioButtonListItem(
        text = "Мобильная камера",
        selected = state,
        onClick = { state = !state }
    )
}
