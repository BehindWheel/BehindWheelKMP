package com.egoriku.grodnoroads.foundation.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
val ColorScheme.isLight
    @Composable
    get() = this.background.luminance() > 0.5f

@Stable
val MaterialTheme.tonalElevation: Dp
    @Composable
    get() = if (colorScheme.isLight) 0.dp else defaultTonalElevation

@Stable
val ColorScheme.surfaceSurfaceVariant: Color
    @Composable
    get() = if (isLight) surface else surfaceVariant

val defaultTonalElevation = 3.dp
val defaultShadowElevation = 3.dp

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

val primaryLight = Color(0xFF232F34)
val lightWhite = Color(0xFFFFFFFF)

private val lightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = lightWhite,
    secondary = colorUnknown,
    onSecondary = colorUnknown,
    primaryContainer = colorUnknown,
    onPrimaryContainer = Color(0xFF79747E),
    secondaryContainer = lightWhite,
    onSecondaryContainer = Color(0xFF1B1B1B),
    error = colorUnknown,
    errorContainer = colorUnknown,
    onError = colorUnknown,
    onErrorContainer = colorUnknown,
    background = lightWhite,
    onBackground = Color(0xFF1C1B1F),
    surface = lightWhite,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF4F4F5),
    onSurfaceVariant = Color(0xFF1C1B1F),
    outline = Color(0xFF79747E),
    inverseOnSurface = colorUnknown,
    inverseSurface = colorUnknown,
    inversePrimary = colorUnknown,
    surfaceTint = primaryLight,
    outlineVariant = Color(0xFFCAC4D0),
    scrim = Color.Black
)

val bg = Color(0xFF1A1F26)
val bgVariant = Color(0xFF393E46)
val primary = Color(0xFF92979F)
val darkWhite = Color(0xFFDDDDDD)

private val darkColorScheme = darkColorScheme(
    primary = primary,
    onPrimary = darkWhite,
    secondary = colorUnknown,
    onSecondary = colorUnknown,
    primaryContainer = colorUnknown,
    onPrimaryContainer = bg,
    secondaryContainer = primary,
    onSecondaryContainer = darkWhite,
    tertiary = colorUnknown,
    onTertiary = colorUnknown,
    tertiaryContainer = colorUnknown,
    onTertiaryContainer = colorUnknown,
    error = colorUnknown,
    errorContainer = colorUnknown,
    onError = colorUnknown,
    onErrorContainer = colorUnknown,
    background = bg,
    onBackground = darkWhite,
    surface = bg,
    onSurface = darkWhite,
    surfaceVariant = bgVariant,
    onSurfaceVariant = darkWhite,
    inverseOnSurface = colorUnknown,
    inverseSurface = colorUnknown,
    inversePrimary = colorUnknown,
    surfaceTint = primary,
    outline = primary,
    outlineVariant = bgVariant,
    scrim = Color.Black
)