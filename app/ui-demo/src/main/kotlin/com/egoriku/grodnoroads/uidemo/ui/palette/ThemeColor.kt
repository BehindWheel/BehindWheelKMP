package com.egoriku.grodnoroads.uidemo.ui.palette

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ThemeColor(
    colorName: String,
    currentColor: Color,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    val colorHex by rememberMutableState(currentColor) {
        currentColor.copy(alpha = 0f).toArgb()
            .toHexString(
                format = HexFormat {
                    number.prefix = "#"
                    number.removeLeadingZeros = true
                    upperCase = true
                }
            )
    }
    Column(
        modifier = modifier
            .clickable(onClick = { onClick(colorHex) })
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = currentColor, shape = RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = LocalContentColor.current.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(shape = RoundedCornerShape(8.dp))
        )
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = colorHex
        )
        Text(
            style = MaterialTheme.typography.bodySmall,
            text = colorName
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun ThemeColorPreview() = GrodnoRoadsM3ThemePreview {
    ThemeColor(
        colorName = "onSecondaryContainer",
        currentColor = MaterialTheme.colorScheme.onPrimary,
        onClick = {}
    )
}
