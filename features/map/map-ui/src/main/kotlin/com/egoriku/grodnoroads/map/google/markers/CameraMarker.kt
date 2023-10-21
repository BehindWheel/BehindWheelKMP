package com.egoriku.grodnoroads.map.google.markers

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.rememberIconMarker
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.google.android.gms.maps.model.BitmapDescriptor

context(MapUpdater)
@Composable
fun CameraMarker(
    position: StableLatLng,
    icon: () -> BitmapDescriptor,
    onClick: () -> Unit
) {
    rememberIconMarker(
        position = position,
        icon = icon,
        onMarkerClick = onClick,
        zIndex = 1f
    )
}