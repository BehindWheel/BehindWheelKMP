package com.egoriku.grodnoroads.maps.compose.marker

import com.egoriku.grodnoroads.maps.compose.core.Marker
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual fun Marker?.inScope(action: Marker.() -> Unit) {
    val scope = this
    if (scope != null) {
        action(scope)
    }
}
