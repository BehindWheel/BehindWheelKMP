package com.egoriku.grodnoroads.foundation.core

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass as WindowCoreSizeClass

@Composable
fun isMediumScreenWidth(): Boolean {
    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
    return windowAdaptiveInfo.windowSizeClass.isMediumScreenWidth()
}

fun WindowCoreSizeClass.isMediumScreenWidth(): Boolean {
    return isWidthAtLeastBreakpoint(WindowCoreSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)
}

fun WindowCoreSizeClass.isExpandedScreenWidth(): Boolean {
    return isWidthAtLeastBreakpoint(WindowCoreSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)
}
