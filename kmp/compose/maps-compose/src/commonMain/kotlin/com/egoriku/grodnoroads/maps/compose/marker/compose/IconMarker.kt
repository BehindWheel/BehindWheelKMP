package com.egoriku.grodnoroads.maps.compose.marker.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.core.Marker
import com.egoriku.grodnoroads.maps.compose.core.remove
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun rememberIconMarker(
    mapUpdater: MapUpdater,
    position: LatLng,
    icon: () -> MarkerImage,
    zIndex: Float = 0.0f,
    title: String? = null,
    onMarkerClick: () -> Unit
): Marker? {
    val updatedOnMarkerClick by rememberUpdatedState(onMarkerClick)
    val updatedIcon by rememberUpdatedState(icon)

    var marker by rememberMutableState<Marker?> { null }

    LaunchedEffect(marker) {
        mapUpdater.clickedMarker
            .filter { it == marker }
            .onEach { updatedOnMarkerClick() }
            .launchIn(this)
    }

    DisposableEffect(position, title) {
        marker = mapUpdater.addMarker(
            MarkerOptions(
                position = position,
                icon = updatedIcon(),
                zIndex = zIndex,
                title = title
            )
        )

        onDispose {
            marker?.remove()
        }
    }

    return marker
}
