package com.egoriku.grodnoroads.guidance.screen.ui.google.marker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.calc.roundDistanceTo
import com.egoriku.grodnoroads.maps.compose.core.currentPosition
import com.egoriku.grodnoroads.maps.compose.core.updatePosition
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.maps.compose.marker.Anchor
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import com.egoriku.grodnoroads.maps.compose.marker.compose.rememberSimpleMarker
import com.egoriku.grodnoroads.maps.compose.marker.inScope
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater
import com.egoriku.grodnoroads.maps.compose.util.animateMarker

const val ANIMATE_DISTANCE_THRESHOLD = 300

@Composable
fun MapUpdater.NavigationMarker(
    appMode: AppMode,
    position: LatLng,
    bearing: Float,
    icon: () -> MarkerImage,
    rotation: Float,
) {
    val marker = rememberSimpleMarker(
        mapUpdater = this,
        markerOptions = {
            MarkerOptions(
                position = position,
                icon = icon(),
                zIndex = 0f,
                anchor = Anchor(0.5f, 0.5f),
                rotation = bearing - rotation
            )
        },
    )

    LaunchedEffect(appMode) {
        marker.inScope {
            updatePosition(position)
        }
    }

    LaunchedEffect(position) {
        marker.inScope {
            if (this.currentPosition roundDistanceTo position > ANIMATE_DISTANCE_THRESHOLD) {
                updatePosition(position)
            } else {
                animateMarker(
                    marker = this,
                    start = this.currentPosition,
                    destination = position,
                    onInterpolated = {
                        this.updatePosition(it)
                    }
                )
            }
        }
    }
}