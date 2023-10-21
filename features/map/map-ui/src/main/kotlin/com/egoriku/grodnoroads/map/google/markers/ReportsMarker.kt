package com.egoriku.grodnoroads.map.google.markers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.rememberIconMarker
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.ui.IconGenerator

context(MapUpdater)
@Composable
fun ReportsMarker(
    position: StableLatLng,
    iconGenerator: () -> IconGenerator,
    message: String,
    onClick: () -> Unit
) {
    val icon = remember(message) {
        {
            BitmapDescriptorFactory.fromBitmap(iconGenerator().makeIcon(message))
        }
    }

    rememberIconMarker(
        position = position,
        icon = icon,
        zIndex = 2f,
        title = message,
        onMarkerClick = onClick
    )
}