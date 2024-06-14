package com.egoriku.grodnoroads.maps.compose.style

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.maps.compose.configuration.MapStyleOptions
import dev.icerock.moko.resources.FileResource

interface MapStyleLoader {

    suspend fun load(fileResource: FileResource): MapStyleOptions
}

@Composable
expect fun rememberMapStyleLoader(): MapStyleLoader