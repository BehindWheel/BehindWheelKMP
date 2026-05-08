package com.egoriku.grodnoroads.guidance.screen.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import com.egoriku.grodnoroads.foundation.theme.LocalPlatform
import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.foundation.theme.Platform.Android
import com.egoriku.grodnoroads.foundation.theme.Platform.IOS
import com.egoriku.grodnoroads.maps.compose.core.Point

@Composable
fun rememberOffsetUtil(): OffsetUtil {
    val density = LocalDensity.current
    val platform = LocalPlatform.current

    return remember { OffsetUtil(density, platform) }
}

class OffsetUtil(
    private val density: Density,
    private val platform: Platform
) {

    fun offsetToPoint(offset: Offset): Point {
        return when (platform) {
            IOS -> {
                Point(
                    x = offset.x / density.density,
                    y = offset.y / density.density
                )
            }
            Android -> {
                Point(
                    x = offset.x,
                    y = offset.y
                )
            }
        }
    }
}
