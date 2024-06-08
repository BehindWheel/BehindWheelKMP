package com.egoriku.grodnoroads.maps.compose.style

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.maps.compose.configuration.MapStyleOptions

interface MapStyleLoader {

    suspend fun load(path: String): MapStyleOptions
}

@Composable
expect fun rememberMapStyleLoader(): MapStyleLoader