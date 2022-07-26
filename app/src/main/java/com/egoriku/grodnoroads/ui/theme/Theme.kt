package com.egoriku.grodnoroads.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Language

@Composable
fun GrodnoRoadsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    language: Language = Language.Russian,
    content: @Composable () -> Unit
) {
    when (language) {
        Language.Russian -> Theme(darkTheme, content)
        Language.Belarusian -> Theme(darkTheme, content)
        Language.English -> Theme(darkTheme, content)
    }
}

@Composable
private fun Theme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    Surface {
        MaterialTheme(
            colors = if (darkTheme) DarkColors else LightColors,
            content = content
        )
    }
}