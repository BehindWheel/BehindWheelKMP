package com.egoriku.grodnoroads.foundation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.isLight

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    colors: CircleButtonColors = CircleButtonDefaults.buttonColors(),
    shape: Shape = ButtonDefaults.shape,
    shadowElevation: Dp = if (enabled) 3.dp else 0.dp,
    tonalElevation: Dp = if (MaterialTheme.colorScheme.isLight) 0.dp else 6.dp,
    content: @Composable RowScope.() -> Unit
) {
    val containerColor = colors.containerColor(enabled).value
    val contentColor = colors.contentColor(enabled).value

    if (onClick == null) {
        Surface(
            modifier = modifier.semantics { role = Role.Button },
            shape = shape,
            color = containerColor,
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation,
            content = {
                ButtonContent(contentColor, content)
            },
        )
    } else {
        Surface(
            onClick = onClick,
            modifier = modifier.semantics { role = Role.Button },
            shape = shape,
            color = containerColor,
            contentColor = contentColor,
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation,
            enabled = enabled,
            content = {
                ButtonContent(contentColor, content)
            },
        )
    }
}

@Composable
private fun ButtonContent(
    contentColor: Color,
    content: @Composable (RowScope.() -> Unit)
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = 56.dp,
                        minHeight = 56.dp
                    )
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

object CircleButtonDefaults {

    @Composable
    fun buttonColors(
        containerColor: Color = MaterialTheme.colorScheme.surface,
        contentColor: Color = contentColorFor(containerColor),
        disabledContainerColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    ) = CircleButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
    )
}

data class CircleButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color
) {
    @Composable
    internal fun containerColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) containerColor else disabledContainerColor)
    }

    @Composable
    internal fun contentColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) contentColor else disabledContentColor)
    }
}

@GrodnoRoadsPreview
@Composable
private fun CircleButtonPreview() = GrodnoRoadsM3ThemePreview {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CircleButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
        CircleButton(onClick = {}, enabled = false) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}