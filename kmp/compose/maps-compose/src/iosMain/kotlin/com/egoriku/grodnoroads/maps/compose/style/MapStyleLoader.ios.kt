package com.egoriku.grodnoroads.maps.compose.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cocoapods.GoogleMaps.GMSMapStyle
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.maps.compose.configuration.MapStyleOptions
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class, ExperimentalForeignApi::class)
private class MapStyleLoaderIos : MapStyleLoader {

    override suspend fun load(path: String): MapStyleOptions {
        return withContext(Dispatchers.Default) {
            val json = Res.readBytes(path).decodeToString()

            GMSMapStyle.styleWithJSONString(style = json, error = null)!!
        }
    }
}

@Composable
actual fun rememberMapStyleLoader(): MapStyleLoader {
    return remember { MapStyleLoaderIos() }
}