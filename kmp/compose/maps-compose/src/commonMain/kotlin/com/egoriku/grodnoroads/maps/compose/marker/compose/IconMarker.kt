package com.egoriku.grodnoroads.maps.compose.marker.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.core.Marker
import com.egoriku.grodnoroads.maps.compose.core.remove
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

context(MapUpdater)
@Composable
fun rememberIconMarker(
    position: LatLng,
    icon: () -> MarkerImage,
    zIndex: Float = 0.0f,
    title: String? = null,
    onMarkerClick: () -> Unit
): Marker? {
    val updatedOnMarkerClick by rememberUpdatedState(onMarkerClick)

    var marker by remember { mutableStateOf<Marker?>(null) }

    LaunchedEffect(marker) {
        clickedMarker
            .filter { it == marker }
            .onEach { updatedOnMarkerClick() }
            .launchIn(this)
    }

    DisposableEffect(position, title) {
        marker = addMarker(
            MarkerOptions(
                position = position,
                icon = icon(),
                zIndex = zIndex,
                title = title,
            )
        )

        onDispose {
            marker?.remove()
        }
    }

    return marker
}
