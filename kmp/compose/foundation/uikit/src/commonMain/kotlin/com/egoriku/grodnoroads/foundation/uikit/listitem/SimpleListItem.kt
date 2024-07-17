package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Play
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@Composable
fun SimpleListItem(
    imageVector: ImageVector? = null,
    text: String,
    onClick: () -> Unit
) {
    BasicListItem(
        touchModifier = Modifier
            .heightIn(min = 48.dp)
            .clickable(onClick = onClick),
        imageVector = imageVector,
        text = text,
        textStyle = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun SimpleListItemPreview() = GrodnoRoadsM3ThemePreview {
    SimpleListItem(
        imageVector = GrodnoRoads.Outlined.Play,
        text = "Test test test",
        onClick = {}
    )
}