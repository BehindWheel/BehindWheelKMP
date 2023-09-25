package com.egoriku.grodnoroads.map.markers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.egoriku.grodnoroads.mapswrapper.MapScope
import com.egoriku.grodnoroads.mapswrapper.core.StableLatLng
import com.google.android.gms.maps.model.BitmapDescriptor

@Composable
fun MapScope.CameraMarker(
    latLng: StableLatLng,
    icon: () -> BitmapDescriptor,
    onClick: () -> Unit
) {
    DisposableEffect(latLng) {
        onDispose {
            removeMarker(latLng.value)
        }
    }

    addMarker(
        position = latLng.value,
        icon = icon(),
        onClick = onClick
    )
}