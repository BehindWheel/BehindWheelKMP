package com.egoriku.grodnoroads.maps.compose.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cocoapods.GoogleMaps.GMSMapStyle
import com.egoriku.grodnoroads.maps.compose.configuration.MapStyleOptions
import dev.icerock.moko.resources.FileResource
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
private class MapStyleLoaderIos : MapStyleLoader {

    override suspend fun load(fileResource: FileResource): MapStyleOptions {
        return GMSMapStyle.styleWithContentsOfFileURL(fileResource.url, error = null)!!
    }
}

@Composable
actual fun rememberMapStyleLoader(): MapStyleLoader {
    return remember { MapStyleLoaderIos() }
}
