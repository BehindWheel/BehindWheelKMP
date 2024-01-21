package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.defaultShadowElevation
import com.egoriku.grodnoroads.foundation.theme.tonalElevation

@Composable
fun ActionButtonGroup(
    shape: Shape = RoundedCornerShape(10.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = shape,
        shadowElevation = defaultShadowElevation,
        tonalElevation = MaterialTheme.tonalElevation
    ) {
        Column(content = content)
    }
}

@Composable
fun ActionIcon(
    imageVector: ImageVector,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            imageVector = imageVector,
            contentDescription = null
        )
    }
}