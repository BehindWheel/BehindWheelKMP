package com.egoriku.grodnoroads.uidemo.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Appearance
import com.egoriku.grodnoroads.foundation.icons.outlined.Moon
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer

@Composable
fun Header(
    onPalette: () -> Unit,
    modifier: Modifier = Modifier,
    onThemeChange: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "UI Demo App")
        WeightSpacer()
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            IconButton(onClick = onPalette) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Appearance,
                    contentDescription = null
                )
            }
        }
        CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
            IconButton(onClick = onThemeChange) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Moon,
                    contentDescription = null
                )
            }
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun HeaderPreview() = GrodnoRoadsM3ThemePreview {
    Header(onThemeChange = {}, onPalette = {})
}
