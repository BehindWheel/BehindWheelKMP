package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview

@Composable
fun DisabledText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DisabledTextPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(32.dp)) {
        DisabledText(text = "Sample text")
    }
}