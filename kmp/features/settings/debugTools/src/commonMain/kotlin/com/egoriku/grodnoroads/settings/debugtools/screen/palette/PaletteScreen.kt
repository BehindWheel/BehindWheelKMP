package com.egoriku.grodnoroads.settings.debugtools.screen.palette

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.settings.debugtools.screen.palette.ui.Material3Palette

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun PaletteScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            SettingsTopBar(
                title = "Color Palette",
                onBack = onBack
            )
        },
        contentWindowInsets = WindowInsets()
    ) {
        Material3Palette(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun PaletteScreenPreview() = GrodnoRoadsM3ThemePreview {
    PaletteScreen(onBack = {})
}
