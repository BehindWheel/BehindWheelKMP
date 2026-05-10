package com.egoriku.grodnoroads.foundation.uikit.dynamic

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.theme.LocalPlatform
import com.egoriku.grodnoroads.foundation.theme.Platform.Android
import com.egoriku.grodnoroads.foundation.theme.Platform.IOS

@Composable
fun RadioButton(
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

    val color = if (enabled) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }

    if (selected) {
        Canvas(
            modifier = modifier
                .minimumInteractiveComponentSize()
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.RadioButton,
                    interactionSource = null,
                    indication = ripple(
                        bounded = false,
                        radius = 20.dp
                    )
                )
                .wrapContentSize(Alignment.Center)
                .padding(2.dp)
                .requiredSize(32.dp)
        ) {
            drawCheck(
                checkColor = color,
                checkFraction = 1f,
                crossCenterGravitation = 0f,
                strokeWidthPx = 1.5.dp.toPx(),
                drawingCache = checkCache
            )
        }
    } else {
        Spacer(modifier = Modifier.minimumInteractiveComponentSize())
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun RadioButtonPreview() = GrodnoRoadsM3ThemePreview {
    Row(modifier = Modifier.padding(16.dp)) {
        RadioButton(selected = true, onClick = {})
        RadioButton(selected = false, onClick = {})
    }
}
