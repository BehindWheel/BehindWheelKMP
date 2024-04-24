package com.egoriku.grodnoroads.maps.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

context(MapUpdater)
@Composable
fun rememberSimpleMarker(
    markerOptions: () -> MarkerOptions,
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

context(MapUpdater)
@Composable
fun rememberIconMarker(
    position: LatLng,
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
        marker = addMarker(
            position = position,
            icon = icon(),
            zIndex = zIndex,
            title = title,
        )

        onDispose {
            marker?.remove()
        }
    }

    return marker
}


inline fun Marker?.inScope(action: Marker.() -> Unit) {
    val scope = this
    if (scope != null) {
        action(scope)
    }
}