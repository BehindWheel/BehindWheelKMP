package com.egoriku.grodnoroads.map.google.markers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.google.android.gms.maps.model.BitmapDescriptor

@Composable
fun MapUpdater.CameraMarker(
    position: StableLatLng,
    icon: () -> BitmapDescriptor,
    onClick: () -> Unit
) {
    DisposableEffect(position) {
        onDispose {
            removeMarker(position.value)
        }
    }

    LaunchedEffect(position) {
        addMarker(
            position = position.value,
            icon = icon(),
            onClick = onClick
        )
    }
}