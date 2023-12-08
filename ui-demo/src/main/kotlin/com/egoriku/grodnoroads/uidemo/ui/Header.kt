package com.egoriku.grodnoroads.uidemo.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    onPalette: () -> Unit,
    onThemeChange: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "UI Demo App")
        Spacer(Modifier.weight(1f))
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            IconButton(onClick = onPalette) {
                Icon(imageVector = Icons.Default.Palette, contentDescription = null)
            }
        }
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            IconButton(onClick = onThemeChange) {
                Icon(imageVector = Icons.Default.Nightlight, contentDescription = null)
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun HeaderPreview() = GrodnoRoadsM3ThemePreview {
    Header(onThemeChange = {}, onPalette = {})
}