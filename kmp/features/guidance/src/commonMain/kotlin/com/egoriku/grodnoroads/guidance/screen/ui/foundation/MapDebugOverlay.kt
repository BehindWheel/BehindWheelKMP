package com.egoriku.grodnoroads.guidance.screen.ui.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight

@Composable
fun MapDebugOverlay(
    mapZoom: Float,
    targetZoom: Float,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            MapDebugRow(label = "Map idle zoom", value = mapZoom.toString())
            MapDebugRow(label = "Target zoom", value = targetZoom.toString())
        }
    }
}

@Composable
private fun MapDebugRow(label: String, value: String) {
    Text(
        text = "$label: $value",
        style = MaterialTheme.typography.labelSmall
    )
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun PreviewMapDebugOverlayPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.background(Color.LightGray)) {
        MapDebugOverlay(
            mapZoom = 14.5f,
            targetZoom = 13.0f,
            modifier = Modifier.padding(16.dp)
        )
    }
}
