package com.egoriku.grodnoroads.foundation.uikit.dynamic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PlatformPreviewProvider
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.theme.LocalPlatform
import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.foundation.theme.Platform.Android
import com.egoriku.grodnoroads.foundation.theme.Platform.IOS

@Composable
fun DynamicRadioButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    when (LocalPlatform.current) {
        Android -> {
            RadioButton(
                modifier = modifier,
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.secondary
                ),
                enabled = enabled,
                selected = selected,
                onClick = onClick
            )
        }
        IOS -> {
            RadioButtonIos(
                selected = selected,
                modifier = modifier,
                enabled = enabled,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun RadioButtonIos(
    selected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val checkCache = remember { CheckDrawingCache() }

    val boxColor = when {
        !enabled && selected -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        selected -> MaterialTheme.colorScheme.secondary
        else -> Color.Transparent
    }
    val checkmarkColor = when {
        !enabled -> Color.Transparent
        selected -> MaterialTheme.colorScheme.onSecondary
        else -> Color.Transparent
    }
    val borderColor = when {
        !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        selected -> Color.Transparent
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Canvas(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .defaultMinSize(24.dp, 24.dp)
            .padding(4.dp)
            .clip(CircleShape)
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.RadioButton,
                interactionSource = null,
                indication = LocalIndication.current
            )
            .wrapContentSize(Alignment.Center)
            .requiredSize(24.dp)
    ) {
        drawCircle(color = boxColor)
        drawCircle(
            color = borderColor,
            style = Stroke(width = 3.dp.toPx())
        )
        drawCheck(
            checkColor = checkmarkColor,
            checkFraction = if (selected) 1f else 0f,
            crossCenterGravitation = 0f,
            strokeWidthPx = 1.5.dp.toPx(),
            drawingCache = checkCache
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun RadioButtonPreview(
    @PreviewParameter(PlatformPreviewProvider::class) platform: Platform
) = GrodnoRoadsM3ThemePreview(platform = platform) {
    Row(modifier = Modifier.padding(16.dp)) {
        DynamicRadioButton(selected = true, onClick = {})
        DynamicRadioButton(selected = false, onClick = {})
    }
}
