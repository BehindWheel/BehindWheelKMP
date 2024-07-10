package com.egoriku.grodnoroads.maps.compose.extension

import com.egoriku.grodnoroads.maps.compose.core.Projection

expect val GoogleMap.zoom: Float
expect val GoogleMap.projection: Projection

expect fun GoogleMap.updatePadding(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0)