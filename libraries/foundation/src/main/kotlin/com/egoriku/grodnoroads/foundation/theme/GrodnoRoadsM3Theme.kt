package com.egoriku.grodnoroads.foundation.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext

val ColorScheme.isLight
    @Composable
    get() = this.background.luminance() > 0.5f

@Composable
fun GrodnoRoadsM3ThemePreview(content: @Composable () -> Unit) {
    GrodnoRoadsM3Theme {
        Surface(content = content)
    }
}

@Composable
fun GrodnoRoadsM3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> if (darkTheme) darkColorScheme else lightColorScheme
    }
    MaterialTheme(colorScheme = colorScheme, content = content)
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = false// Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

val colorUnknown = Color(0xFFE100FF)

private val lightColorScheme = lightColorScheme(
    primary = Color(0xFF232F34),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFCE7D2D),
    onSecondary = Color(0xFFFFFFFF),
    primaryContainer = colorUnknown,
    onPrimaryContainer = Color(0xFF1B1B1B),
    secondaryContainer = Color(0xFFE4E5E6),
    onSecondaryContainer = Color(0xFF1B1B1B),
    error = Color(0xFFB3261E),
    errorContainer = Color(0xFFF9DEDC),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xFF1C1B1F),
    outline = Color(0xFF79747E),
    inverseOnSurface = colorUnknown,
    inverseSurface = colorUnknown,
    inversePrimary = colorUnknown,
    surfaceTint = Color(0xFF232F34),
    outlineVariant = Color(0xFFCAC4D0),
    scrim = Color(0xFF000000)
)

private val darkColorScheme = darkColorScheme(
    primary = Color(0xFF92979F),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFFCE7D2D),
    onSecondary = Color(0xFFDDDDDD),
    primaryContainer = colorUnknown,
    onPrimaryContainer = Color(0xFF92979F),
    secondaryContainer = Color(0xFF303E52),
    onSecondaryContainer = Color(0xFFDDDDDD),
    tertiary = colorUnknown,
    onTertiary = colorUnknown,
    tertiaryContainer = colorUnknown,
    onTertiaryContainer = colorUnknown,
    error = Color(0xFFF2B8B5),
    errorContainer = Color(0xFF8C1D18),
    onError = Color(0xFF8C1D18),
    onErrorContainer = Color(0xFFF9DEDC),
    background = Color(0xFF242f3e),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF242f3e),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF242f3e),
    onSurfaceVariant = Color(0xFFE6E1E5),
    outline = Color(0xFF938F99),
    inverseOnSurface = colorUnknown,
    inverseSurface = colorUnknown,
    inversePrimary = colorUnknown,
    surfaceTint = Color(0xFF92979F),
    outlineVariant = Color(0xFF2B3749),
    scrim = Color(0xFF000000)
)