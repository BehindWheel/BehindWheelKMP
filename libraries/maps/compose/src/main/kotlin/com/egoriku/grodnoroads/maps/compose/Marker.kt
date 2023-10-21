package com.egoriku.grodnoroads.maps.compose

import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

context(MapUpdater)
@Composable
fun rememberSimpleMarker(
    tag: String,
    position: StableLatLng,
    icon: () -> BitmapDescriptor,
    zIndex: Float = 0.0f,
    anchor: Offset = Offset(0.5f, 1.0f),
    rotation: Float = 0.0f,
): Marker? {
    var marker = remember<Marker?> { null }

    DisposableEffect(tag) {
        addMarker(
            position = position.value,
            icon = icon(),
            zIndex = zIndex,
            anchor = anchor,
            rotation = rotation
        ).also {
            marker = it
        }

        onDispose {
            marker?.remove()
        }
    }

    return marker
}

context(MapUpdater)
@Composable
fun rememberIconMarker(
    position: StableLatLng,
    icon: () -> BitmapDescriptor,
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
        addMarker(
            position = position.value,
            icon = icon(),
            zIndex = zIndex,
            title = title,
        ).also {
            marker = it
        }
        onDispose {
            marker?.remove()
        }
    }

    return marker
}