package com.egoriku.grodnoroads.foundation.core

import androidx.window.core.layout.WindowSizeClass as WindowCoreSizeClass

fun WindowCoreSizeClass.isMediumScreenWidth(): Boolean {
    return isWidthAtLeastBreakpoint(WindowCoreSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
}

fun WindowCoreSizeClass.isExpandedScreenWidth(): Boolean {
    return isWidthAtLeastBreakpoint(WindowCoreSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
}
