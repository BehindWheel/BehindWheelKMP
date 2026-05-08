package com.egoriku.grodnoroads.foundation.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.foundation.theme.LocalPlatform
import com.egoriku.grodnoroads.foundation.theme.Platform

@Composable
fun GrodnoRoadsM3ThemePreview(
    modifier: Modifier = Modifier,
    platform: Platform = Platform.Android,
    content: @Composable () -> Unit
) {
    GrodnoRoadsM3Theme(typography = MaterialTheme.typography) {
        CompositionLocalProvider(LocalPlatform provides platform) {
            Surface(
                modifier = modifier,
                content = content
            )
        }
    }
}
