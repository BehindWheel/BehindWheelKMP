package com.egoriku.grodnoroads.guidance.screen.ui.google.marker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.core.setIcon
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.maps.compose.marker.compose.rememberIconMarker
import com.egoriku.grodnoroads.maps.compose.marker.inScope
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater

@Composable
fun MapUpdater.CameraMarker(
    position: LatLng,
    markerSize: MarkerSize,
    icon: () -> MarkerImage,
    zIndex: Float = 1f,
    onClick: () -> Unit
) {
    val updatedIcon by rememberUpdatedState(icon)

    val marker = rememberIconMarker(
        mapUpdater = this,
        position = position,
        icon = updatedIcon,
        onMarkerClick = onClick,
        zIndex = zIndex
    )

    LaunchedEffect(markerSize) {
        marker.inScope {
            setIcon(updatedIcon())
        }
    }
}
