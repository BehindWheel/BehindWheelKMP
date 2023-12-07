package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Composable
fun SecondaryCircleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        contentPadding = PaddingValues(0.dp),
        modifier = modifier.size(48.dp, 48.dp),
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun SecondaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(16.dp)) {
        SecondaryCircleButton(onClick = {}) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
        }
    }
}