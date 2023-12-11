package com.egoriku.grodnoroads.uidemo.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer

@Composable
fun UIDemoContainer(
    name: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(0.5.dp, Color.Gray)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = name, style = MaterialTheme.typography.titleMedium)
        VerticalSpacer(8.dp)
        content()
    }
}

@Preview
@Composable
private fun UIDemoContainerPreview() = GrodnoRoadsM3ThemePreview {
    UIDemoContainer("Test") {
        VerticalSpacer(dp = 46.dp)
    }
}