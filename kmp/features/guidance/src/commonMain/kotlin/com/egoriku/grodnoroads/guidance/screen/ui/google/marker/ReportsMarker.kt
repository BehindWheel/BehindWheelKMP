package com.egoriku.grodnoroads.guidance.screen.ui.google.marker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerGenerator
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.maps.compose.core.setIcon
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.maps.compose.marker.compose.rememberIconMarker
import com.egoriku.grodnoroads.maps.compose.marker.inScope
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater

context(MapUpdater)
@Composable
fun ReportsMarker(
    position: LatLng,
    markerSize: MarkerSize,
    markerGenerator: () -> MarkerGenerator,
    iconProvider: () -> MarkerImage?,
    message: String,
    onClick: () -> Unit
) {
    val icon = remember(message, markerSize) {
        {
            when (markerSize) {
                MarkerSize.Large -> markerGenerator().makeIcon(message)
                MarkerSize.Small -> {
                    iconProvider() ?: markerGenerator().makeIcon(message)
                }
            }
        }
    }

    val marker = rememberIconMarker(
        position = position,
        icon = icon,
        zIndex = 2f,
        title = message,
        onMarkerClick = onClick
    )

    LaunchedEffect(markerSize) {
        marker.inScope {
            setIcon(icon())
        }
    }
}