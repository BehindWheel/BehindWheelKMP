package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable

@Composable
fun GrodnoRoadsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Surface {
        MaterialTheme(
            colors = if (darkTheme) DarkColors else LightColors,
            content = content
        )
    }
}