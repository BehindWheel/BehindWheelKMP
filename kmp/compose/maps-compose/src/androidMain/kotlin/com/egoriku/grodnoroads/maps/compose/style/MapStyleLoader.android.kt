package com.egoriku.grodnoroads.maps.compose.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.maps.compose.configuration.MapStyleOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.ExperimentalResourceApi

private class MapStyleLoaderAndroid : MapStyleLoader {

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun load(path: String): MapStyleOptions {
        return withContext(Dispatchers.Default) {
            val json = Res.readBytes(path).decodeToString()

            MapStyleOptions(json)
        }
    }
}

@Composable
actual fun rememberMapStyleLoader(): MapStyleLoader {
    return remember { MapStyleLoaderAndroid() }
}