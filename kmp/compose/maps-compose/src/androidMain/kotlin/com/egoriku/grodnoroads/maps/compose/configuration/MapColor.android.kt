package com.egoriku.grodnoroads.maps.compose.configuration

import com.egoriku.grodnoroads.maps.compose.configuration.MapColor.Dark
import com.egoriku.grodnoroads.maps.compose.configuration.MapColor.Light
import com.egoriku.grodnoroads.maps.compose.configuration.MapColor.System
import com.google.android.gms.maps.model.MapColorScheme

fun MapColor.toAndroidColorScheme() = when (this) {
    Light -> MapColorScheme.LIGHT
    Dark -> MapColorScheme.DARK
    System -> MapColorScheme.FOLLOW_SYSTEM
}
