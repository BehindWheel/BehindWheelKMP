package com.egoriku.grodnoroads.guidance.screen.ui.google

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage

expect class MarkerGenerator {
    fun makeIcon(text: String): MarkerImage
}

@Composable
expect fun rememberMarkerGenerator(): MarkerGenerator
