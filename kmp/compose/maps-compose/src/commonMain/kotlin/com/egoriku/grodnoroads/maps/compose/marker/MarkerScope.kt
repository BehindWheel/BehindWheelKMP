package com.egoriku.grodnoroads.maps.compose.marker

import com.egoriku.grodnoroads.maps.compose.core.Marker

expect fun Marker?.inScope(action: Marker.() -> Unit)