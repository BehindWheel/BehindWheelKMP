package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Small

@Composable
fun PrimaryCircleButton(
    onClick: () -> Unit,
    size: Size,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    val buttonSize = when (size) {
        Large -> 56.dp
        Small -> 48.dp
    }
    Button(
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
        modifier = modifier.size(buttonSize),
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun PrimaryCircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrimaryCircleButton(size = Large, onClick = {}) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }
        PrimaryCircleButton(size = Small, onClick = {}) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
        }

    }
}