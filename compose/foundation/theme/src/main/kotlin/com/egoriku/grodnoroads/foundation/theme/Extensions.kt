package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val defaultTonalElevation = 3.dp
val defaultShadowElevation = 3.dp

@Stable
val ColorScheme.isLight
    @Composable
    get() = background.luminance() > 0.5f

@Stable
val MaterialTheme.tonalElevation: Dp
    @Composable
    get() = if (colorScheme.isLight) 0.dp else defaultTonalElevation
