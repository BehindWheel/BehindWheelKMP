package com.egoriku.grodnoroads.foundation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier.size(ButtonDefaults.MinWidth),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
        shape = CircleShape,
        contentPadding = contentPadding,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@Preview
@Composable
private fun CircleButtonPreview() {
    GrodnoRoadsTheme {
        CircleButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}