package com.egoriku.grodnoroads.settings.map.screen.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview

@Composable
fun CheckableCard(
    title: String,
    selected: Boolean,
    imageUrl: String,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.width(120.dp)) {
        Card(
            modifier = Modifier.size(120.dp),
            onClick = onClick,
            shape = RoundedCornerShape(10.dp),
            border = when {
                selected -> BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface)
                else -> null
            }
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                placeholder = ColorPainter(MaterialTheme.colorScheme.outlineVariant),
                model = imageUrl,
                contentDescription = null
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun CheckableCardPreview() = GrodnoRoadsM3ThemePreview {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CheckableCard(
            title = "Minimal",
            selected = false,
            imageUrl = "",
            onClick = {},
        )
        CheckableCard(
            title = "Detailed",
            selected = true,
            imageUrl = "",
            onClick = {},
        )
    }
}