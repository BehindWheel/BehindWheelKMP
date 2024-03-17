package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val colorUnknown = Color(0xFFE100FF)

val Red = Color(0xFFB3261E)

internal val lightColorScheme = lightColorScheme(
    primary = Color(0xFF232F34),
    onPrimary = Color.White,
    primaryContainer = Color.White,
    onPrimaryContainer = Color(0xFF29373D),
    inversePrimary = Color.White,

    secondary = Color(0xFFCC9600),
    onSecondary = Color.White,
    secondaryContainer = Color.White,
    onSecondaryContainer = Color(0xFF1B1B1B),

    tertiary = colorUnknown,
    tertiaryContainer = colorUnknown,
    onTertiary = colorUnknown,
    onTertiaryContainer = colorUnknown,

    surface = Color.White,
    surfaceVariant = Color.White,
    onSurface = Color(0xFF232F34),
    surfaceContainer = Color(0xFFFDFDFD),
    onSurfaceVariant = Color(0xFF232F34),
    inverseSurface = Color(0xFF1A1F26),
    inverseOnSurface = Color(0xFFE3E6E8),

    background = Color(0xFFF1F3F3),
    onBackground = Color(0xFF1C1B1F),

    outline = Color(0xFFA3ACB1),
    outlineVariant = Color(0xFFE3E6E8),

    scrim = Color.Black,

    error = Red,
    onError = Color.White,
    errorContainer = Color(0xFFFBEAE9),
    onErrorContainer = Color.Black
)

internal val darkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF232F34),
    primaryContainer = Color(0xFF29373D),
    onPrimaryContainer = Color.White,
    inversePrimary = Color(0xFF393E46),

    secondary = Color(0xFFFFC727),
    onSecondary = Color(0xFF664B00),
    secondaryContainer = Color(0xFF92979F),
    onSecondaryContainer = Color(0xFFFDFDFD),

    tertiary = colorUnknown,
    onTertiary = colorUnknown,
    tertiaryContainer = colorUnknown,
    onTertiaryContainer = colorUnknown,

    surface = Color(0xFF393E46),
    surfaceVariant = Color(0xFF393E46),
    onSurface = Color(0xFFFDFDFD),
    onSurfaceVariant = Color(0xFFE3E6E8),
    inverseSurface = Color(0xFFE3E6E8),
    inverseOnSurface = Color(0xFF2E3538),

    background = Color(0xFF171A1C),
    onBackground = Color(0xFFDDDDDD),

    outline = Color(0xFF8F9DA3),
    outlineVariant = Color(0xFF5C6A70),

    scrim = Color.Black,

    error = Color(0xFFE25850),
    onError = Color(0xFF57120F),
    errorContainer = Color(0xFFAF251D),
    onErrorContainer = Color(0xFFAF251D),
)