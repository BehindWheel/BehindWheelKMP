package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun GrodnoRoadsM3ThemePreview(content: @Composable () -> Unit) {
    GrodnoRoadsM3Theme {
        Surface(content = content)
    }
}