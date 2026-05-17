package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp

@Composable
fun GrodnoRoadsM3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = grodnoRoadsTypography(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) darkColorScheme else lightColorScheme,
        shapes = Shapes(
            medium = RoundedCornerShape(18.dp)
        ),
        typography = typography
    ) {
        CompositionLocalProvider(
            LocalIndication provides platformIndication(),
            LocalRippleConfiguration provides platformRippleConfiguration()
        ) {
            content()
        }
    }
}
