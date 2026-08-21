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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PlatformPreviewProvider
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.foundation.uikit.dynamic.DynamicRadioButton

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
        DynamicRadioButton(
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
        Spacer(12.dp)
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun RadioButtonListItemPreview(
    @PreviewParameter(PlatformPreviewProvider::class) platform: Platform
) = GrodnoRoadsM3ThemePreview(platform = platform) {
    var state by rememberMutableState { true }

    RadioButtonListItem(
        text = "Мобильная камера",
        selected = state,
        onClick = { state = !state }
    )
}
