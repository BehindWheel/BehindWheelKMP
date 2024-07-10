package com.egoriku.grodnoroads.maps.compose.extension

import com.egoriku.grodnoroads.maps.compose.core.Projection
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIEdgeInsetsMake

@OptIn(ExperimentalForeignApi::class)
actual val GoogleMap.zoom: Float
    get() = camera.zoom

@OptIn(ExperimentalForeignApi::class)
actual val GoogleMap.projection: Projection
    get() = projection

@OptIn(ExperimentalForeignApi::class)
actual fun GoogleMap.updatePadding(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) {
    val padding = UIEdgeInsetsMake(
        top = top.toDouble(),
        left = left.toDouble(),
        bottom = bottom.toDouble(),
        right = right.toDouble()
    )
    setPadding(padding)
}
