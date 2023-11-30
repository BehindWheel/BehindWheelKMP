package com.egoriku.grodnoroads.map.google.markers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.egoriku.grodnoroads.map.google.MarkerSize
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.inScope
import com.egoriku.grodnoroads.maps.compose.rememberIconMarker
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng

context(MapUpdater)
@Composable
fun CameraMarker(
    position: LatLng,
    markerSize: MarkerSize,
    icon: () -> BitmapDescriptor,
    onClick: () -> Unit
) {
    val marker = rememberIconMarker(
        position = position,
        icon = icon,
        onMarkerClick = onClick,
        zIndex = 1f
    )

    LaunchedEffect(markerSize) {
        marker.inScope {
            setIcon(icon())
        }
    }
}