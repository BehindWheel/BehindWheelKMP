package com.egoriku.grodnoroads.foundation.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme

@Composable
fun GrodnoRoadsM3ThemePreview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    GrodnoRoadsM3Theme {
        Surface(
            modifier = modifier,
            content = content
        )
    }
}
