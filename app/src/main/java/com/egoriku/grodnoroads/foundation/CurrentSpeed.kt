package com.egoriku.grodnoroads.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrentSpeed(
    modifier: Modifier = Modifier,
    speed: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.White, shape = CircleShape)
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)

                val currentHeight = placeable.height
                var heightCircle = currentHeight
                if (placeable.width > heightCircle)
                    heightCircle = placeable.width

                layout(heightCircle, heightCircle) {
                    placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                }
            }
    ) {
        Text(
            text = speed,
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(4.dp)
                .defaultMinSize(48.dp)
        )
    }
}

@Preview
@Composable
fun CurrentSpeedPreview() {
    CurrentSpeed(speed = 70.toString())
}