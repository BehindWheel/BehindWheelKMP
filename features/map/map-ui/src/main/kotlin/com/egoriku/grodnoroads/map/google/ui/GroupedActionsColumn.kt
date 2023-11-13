package com.egoriku.grodnoroads.map.google.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.defaultShadowElevation
import com.egoriku.grodnoroads.foundation.theme.surfaceSurfaceVariant
import com.egoriku.grodnoroads.foundation.theme.tonalElevation

@Composable
fun GroupedActionsColumn(
    shape: Shape = RoundedCornerShape(10.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = shape,
        shadowElevation = defaultShadowElevation,
        color = MaterialTheme.colorScheme.surfaceSurfaceVariant,
        tonalElevation = MaterialTheme.tonalElevation
    ) {
        Column(content = content)
    }
}