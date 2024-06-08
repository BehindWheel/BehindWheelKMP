package com.egoriku.grodnoroads.maps.compose.marker.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.maps.compose.core.Marker
import com.egoriku.grodnoroads.maps.compose.core.remove
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater

context(MapUpdater)
@Composable
fun rememberSimpleMarker(
    markerOptions: () -> MarkerOptions
): Marker? {
    var marker by remember { mutableStateOf<Marker?>(null) }

    DisposableEffect(Unit) {
        marker = addMarker(markerOptions = markerOptions())

        onDispose {
            marker?.remove()
        }
    }

    return marker
}