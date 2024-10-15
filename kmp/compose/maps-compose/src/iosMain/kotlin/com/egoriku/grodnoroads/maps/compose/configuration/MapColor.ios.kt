package com.egoriku.grodnoroads.maps.compose.configuration

import com.egoriku.grodnoroads.maps.compose.configuration.MapColor.Dark
import com.egoriku.grodnoroads.maps.compose.configuration.MapColor.Light
import com.egoriku.grodnoroads.maps.compose.configuration.MapColor.System
import platform.UIKit.UIUserInterfaceStyle.UIUserInterfaceStyleDark
import platform.UIKit.UIUserInterfaceStyle.UIUserInterfaceStyleLight
import platform.UIKit.UIUserInterfaceStyle.UIUserInterfaceStyleUnspecified

fun MapColor.toIosColorScheme() = when (this) {
    Light -> UIUserInterfaceStyleLight
    Dark -> UIUserInterfaceStyleDark
    System -> UIUserInterfaceStyleUnspecified
}
