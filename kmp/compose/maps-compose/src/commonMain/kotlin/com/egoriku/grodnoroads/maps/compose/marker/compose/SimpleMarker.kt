package com.egoriku.grodnoroads.maps.compose.marker.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.maps.compose.core.Marker
import com.egoriku.grodnoroads.maps.compose.core.remove
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater

@Composable
fun rememberSimpleMarker(
    mapUpdater: MapUpdater,
    markerOptions: () -> MarkerOptions
): Marker? {
    val updatedMarkerOptions by rememberUpdatedState(markerOptions)

    var marker by rememberMutableState<Marker?> { null }

    DisposableEffect(Unit) {
        marker = mapUpdater.addMarker(markerOptions = updatedMarkerOptions())

        onDispose {
            marker?.remove()
        }
    }

    return marker
}
