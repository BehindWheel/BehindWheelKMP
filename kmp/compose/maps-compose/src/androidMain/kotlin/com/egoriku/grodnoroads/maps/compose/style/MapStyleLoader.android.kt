package com.egoriku.grodnoroads.maps.compose.style

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.maps.compose.configuration.MapStyleOptions
import dev.icerock.moko.resources.FileResource

private class MapStyleLoaderAndroid(private val context: Context) : MapStyleLoader {

    override suspend fun load(fileResource: FileResource): MapStyleOptions {
        return MapStyleOptions.loadRawResourceStyle(context, fileResource.rawResId)
    }
}

@Composable
actual fun rememberMapStyleLoader(): MapStyleLoader {
    val context = LocalContext.current
    return remember { MapStyleLoaderAndroid(context) }
}