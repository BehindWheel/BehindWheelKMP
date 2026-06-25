package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier.heightIn(min = 48.dp),
        enabled = enabled,
        onClick = onClick,
        content = {
            Text(text = text)
        }
    )
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun PrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    PrimaryButton(
        modifier = Modifier.padding(16.dp),
        onClick = {},
        text = "Primary Button"
    )
}
