package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_play
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun SimpleListItem(
    drawableResource: DrawableResource? = null,
    text: String,
    onClick: () -> Unit
) {
    BasicListItem(
        touchModifier = Modifier
            .heightIn(min = 48.dp)
            .clickable(onClick = onClick),
        drawableResource = drawableResource,
        text = text,
        textStyle = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun SimpleListItemPreview() = GrodnoRoadsM3ThemePreview {
    SimpleListItem(
        drawableResource = Res.drawable.ic_play,
        text = "Test test test",
        onClick = {}
    )
}