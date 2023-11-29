package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val colorUnknown = Color(0xFFE100FF)

internal val primaryLight = Color(0xFF232F34)
internal val lightWhite = Color(0xFFFFFFFF)

internal val yellow = Color(0xFFFAED35)
internal val bg = Color(0xFF1A1F26)

internal val lightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = lightWhite,
    secondary = colorUnknown,
    onSecondary = colorUnknown,
    primaryContainer = colorUnknown,
    onPrimaryContainer = Color(0xFF79747E),
    secondaryContainer = lightWhite,
    onSecondaryContainer = Color(0xFF1B1B1B),
    tertiary = yellow,
    tertiaryContainer = bg,
    onTertiary = lightWhite,
    onTertiaryContainer = lightWhite,
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


internal val bgVariant = Color(0xFF393E46)
internal val primary = Color(0xFF92979F)
internal val darkWhite = Color(0xFFDDDDDD)

internal val darkColorScheme = darkColorScheme(
    primary = primary,
    onPrimary = darkWhite,
    secondary = colorUnknown,
    onSecondary = colorUnknown,
    primaryContainer = colorUnknown,
    onPrimaryContainer = bg,
    secondaryContainer = primary,
    onSecondaryContainer = darkWhite,
    tertiary = yellow,
    onTertiary = darkWhite,
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