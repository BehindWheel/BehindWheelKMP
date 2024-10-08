package com.egoriku.grodnoroads.maps.compose.extension

import com.egoriku.grodnoroads.maps.compose.core.Projection

actual val GoogleMap.zoom: Float
    get() = cameraPosition.zoom

actual val GoogleMap.projection: Projection
    get() = projection

actual fun GoogleMap.updatePadding(left: Int, top: Int, right: Int, bottom: Int) {
    setPadding(left, top, right, bottom)
}
