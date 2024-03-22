package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.foundation.theme.tonalElevation
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size

@Composable
fun PrimaryInverseCircleButton(
    onClick: () -> Unit,
    size: Size,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val minSize = when (size) {
        Size.Small -> 48.dp
        Size.Large -> 56.dp
    }
    val contentColor = MaterialTheme.colorScheme.primary
    val shadowColor = when {
        MaterialTheme.colorScheme.isLight -> MaterialTheme.colorScheme.outline
        else -> Color.Black
    }
    Surface(
        modifier = modifier
            .semantics { role = Role.Button }
            .shadow(
                elevation = 6.dp,
                shape = CircleShape,
                ambientColor = shadowColor,
                spotColor = shadowColor
            ),
        onClick = onClick,
        color = MaterialTheme.colorScheme.inversePrimary,
        contentColor = contentColor,
        shape = CircleShape,
        tonalElevation = MaterialTheme.tonalElevation,
        shadowElevation = 0.dp,
        content = {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                Row(
                    Modifier
                        .defaultMinSize(minWidth = minSize, minHeight = minSize)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    content = content
                )
            }
        },
    )
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun PrimaryInverseCircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    Column(modifier = Modifier.padding(16.dp)) {
        PrimaryInverseCircleButton(onClick = {}, size = Size.Large) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        }
        PrimaryInverseCircleButton(onClick = {}, size = Size.Small) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }
}