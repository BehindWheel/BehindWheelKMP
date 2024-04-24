package com.egoriku.grodnoroads.foundation.common.ui.dialog.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.MR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    textResId: Int,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        TextButton(
            enabled = enabled,
            shape = RoundedCornerShape(0.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = modifier,
            onClick = onClick
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = textResId),
                color = when {
                    enabled -> MaterialTheme.colorScheme.onSurface
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                }
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
fun PreviewDialogButton() = GrodnoRoadsM3ThemePreview {
    Column {
        DialogButton(
            modifier = Modifier.fillMaxWidth(),
            textResId = MR.strings.ok.resourceId
        ) {}
        DialogButton(
            modifier = Modifier.fillMaxWidth(),
            textResId = MR.strings.cancel.resourceId
        ) {}
        DialogButton(
            modifier = Modifier.fillMaxWidth(),
            textResId = MR.strings.cancel.resourceId,
            enabled = false
        ) {}
    }
}