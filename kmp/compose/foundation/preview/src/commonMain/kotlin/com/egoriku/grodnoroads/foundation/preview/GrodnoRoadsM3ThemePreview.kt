package com.egoriku.grodnoroads.foundation.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme

@Composable
fun GrodnoRoadsM3ThemePreview(content: @Composable () -> Unit) {
    GrodnoRoadsM3Theme {
        Surface(content = content)
    }
}