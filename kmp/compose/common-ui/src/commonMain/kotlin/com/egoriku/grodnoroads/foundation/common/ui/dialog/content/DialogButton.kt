package com.egoriku.grodnoroads.foundation.common.ui.dialog.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.cancel
import com.egoriku.grodnoroads.compose.resources.ok
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import org.jetbrains.compose.resources.stringResource

@Composable
fun DialogButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        TextButton(
            enabled = enabled,
            shape = RoundedCornerShape(0.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = modifier,
            onClick = onClick
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = text,
                color = when {
                    enabled -> MaterialTheme.colorScheme.onSurface
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                }
            )
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun PreviewDialogButtonPreview() = GrodnoRoadsM3ThemePreview {
    Column {
        DialogButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.ok)
        ) {}
        DialogButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.cancel)
        ) {}
        DialogButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.cancel),
            enabled = false
        ) {}
    }
}
