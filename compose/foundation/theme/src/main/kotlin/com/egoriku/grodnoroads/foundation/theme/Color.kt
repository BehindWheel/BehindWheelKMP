package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val colorUnknown = Color(0xFFE100FF)

internal val lightColorScheme = lightColorScheme(
    primary = Color(0xFF232F34),
    onPrimary = Color.White,
    // in figma
    // primaryContainer = Color(0xFF29373D),
    // onPrimaryContainer = Color(0xFFFFFFFF),
    onPrimaryContainer = Color(0xFF29373D),
    primaryContainer = Color.White,
    inversePrimary = Color.White,

    secondary = Color(0xFFCC9600),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFF1CC),
    // onSecondaryContainer = Color(0xFF332500),
    onSecondaryContainer = Color(0xFF1C1B1F),

    tertiary = colorUnknown,
    tertiaryContainer = colorUnknown,
    onTertiary = colorUnknown,
    onTertiaryContainer = colorUnknown,

    surface = Color.White,
    onSurface = Color(0xFF232F34),
    onSurfaceVariant = Color(0xFF5C6A70),
    // inverseSurface = Color(0xFF2E3538),
    inverseSurface = Color(0xFF1A1F26),
    inverseOnSurface = Color(0xFFE3E6E8),

    background = Color(0xFFF1F3F3),

    outline = Color(0xFFABB5BA),
    outlineVariant = Color(0xFFE3E6E8),

    scrim = Color.Black,

    error = Color(0xFFB3261E),
    onError = Color.White,
    errorContainer = Color(0xFFF8D5D3),

    // no colors
    onErrorContainer = colorUnknown,
    onBackground = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF4F4F5),
)

internal val darkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF232F34),
    // in figma
    // primaryContainer = Color(0xFFF0F3F5),
    // onPrimaryContainer = Color(0xFF29373D),
    onPrimaryContainer = Color(0xFFF0F3F5),
    primaryContainer = Color(0xFF29373D),
    inversePrimary = Color(0xFF232F34),

    secondary = Color(0xFFFFC727),
    onSecondary = Color(0xFF664B00),
    secondaryContainer = Color(0xFF997000),
    // onSecondaryContainer = Color(0xFFFFF1CC),
    onSecondaryContainer = Color(0xFFFDFDFD),

    tertiary = colorUnknown,
    onTertiary = colorUnknown,
    tertiaryContainer = colorUnknown,
    onTertiaryContainer = colorUnknown,

    // surface = Color(0xFF2E3538),
    surface = Color(0xFF1A1F26),
    onSurface = Color(0xFFFDFDFD),
    onSurfaceVariant = Color(0xFFE3E6E8),
    inverseSurface = Color(0xFFE3E6E8),
    inverseOnSurface = Color(0xFF2E3538),

    background = Color(0xFF171A1C),

    outline = Color(0xFF454F54),
    outlineVariant = Color(0xFF5C6A70),

    scrim = Color.Black,

    error = Color(0xFFF0ABA8),
    onError = Color(0xFF57120F),
    errorContainer = Color(0xFFAF251D),

    // no colors
    onErrorContainer = colorUnknown,
    onBackground = Color(0xFFDDDDDD),
    surfaceVariant = Color(0xFF393E46),
)