package com.egoriku.grodnoroads.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language

@Composable
fun LanguageBasedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    language: Language = Language.Russian,
    content: @Composable () -> Unit
) {
    when (language) {
        Language.Russian -> GrodnoRoadsTheme(darkTheme, content)
        Language.Belarusian -> GrodnoRoadsTheme(darkTheme, content)
        Language.English -> GrodnoRoadsTheme(darkTheme, content)
    }
}