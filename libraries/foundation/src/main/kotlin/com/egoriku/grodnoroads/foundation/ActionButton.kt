package com.egoriku.grodnoroads.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.*

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    rotation: Float = 0f,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        shadowElevation = defaultShadowElevation,
        color = MaterialTheme.colorScheme.surfaceSurfaceVariant,
        tonalElevation = MaterialTheme.tonalElevation
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Icon(
                modifier = Modifier.rotate(rotation),
                imageVector = imageVector,
                contentDescription = null
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun ActionButtonPreview() = GrodnoRoadsM3ThemePreview {
    ActionButton(imageVector = Icons.Default.Close) {}
}