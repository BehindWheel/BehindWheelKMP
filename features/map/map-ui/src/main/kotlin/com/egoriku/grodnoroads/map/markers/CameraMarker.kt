package com.egoriku.grodnoroads.map.markers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.egoriku.grodnoroads.extensions.unConsume
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun CameraMarker(
    camera: MapEvent.Camera,
    provideIcon: () -> BitmapDescriptor,
    onClick: () -> Unit
) {
    // https://github.com/googlemaps/android-maps-compose/issues/46
    val markerState = rememberMarkerState(position = camera.position)

    LaunchedEffect(key1 = camera) {
        markerState.position = camera.position
    }

    Marker(
        state = markerState,
        icon = provideIcon(),
        onClick = {
            unConsume {
                onClick()
            }
        }
    )
}