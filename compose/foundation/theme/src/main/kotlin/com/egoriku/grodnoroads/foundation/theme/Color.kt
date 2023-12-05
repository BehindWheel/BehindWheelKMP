package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val colorUnknown = Color(0xFFE100FF)

internal val primaryLight = Color(0xFF232F34)
internal val lightWhite = Color(0xFFFFFFFF)

internal val bg = Color(0xFF1A1F26)

internal val lightColorScheme = lightColorScheme(
    primary = Color(0xFF232F34),
    onPrimary = Color(0xFFFFFFFF),

    secondary = Color(0xFFCC9600),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFF1CC),
    onSecondaryContainer = Color(0xFF332500),

    outline = Color(0xFFABB5BA),


    primaryContainer = Color(0xFF29373D),
    onPrimaryContainer = Color(0xFFFFFFFF),
    tertiary = colorUnknown,
    tertiaryContainer = colorUnknown,
    onTertiary = colorUnknown,
    onTertiaryContainer = colorUnknown,
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

    inverseSurface = Color(0xFF29373D),
    inverseOnSurface = Color(0xFFF4F4F5),

    inversePrimary = colorUnknown,
    surfaceTint = primaryLight,
    outlineVariant = Color(0xFFCAC4D0),
    scrim = Color.Black
)

internal val bgVariant = Color(0xFF393E46)
internal val primary = Color(0xFF92979F)
internal val darkWhite = Color(0xFFDDDDDD)

internal val darkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF232F34),

    secondary = Color(0xFFFFC727),
    onSecondary = Color(0xFF664B00),
    secondaryContainer = Color(0xFF997000),
    onSecondaryContainer = Color(0xFFFFF1CC),

    outline = Color(0xFF454F54),


    primaryContainer = Color(0xFF29373D),
    onPrimaryContainer = Color(0xFFFFFFFF),
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

    inverseSurface = Color(0xFF29373D),
    inverseOnSurface = Color(0xFFF4F4F5),

    onSurfaceVariant = darkWhite,
    inversePrimary = colorUnknown,
    surfaceTint = primary,
    outlineVariant = bgVariant,
    scrim = Color.Black
)