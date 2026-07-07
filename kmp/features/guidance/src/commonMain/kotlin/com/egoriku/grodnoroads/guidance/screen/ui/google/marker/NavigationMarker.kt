package com.egoriku.grodnoroads.guidance.screen.ui.google.marker

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.calc.roundDistanceTo
import com.egoriku.grodnoroads.maps.compose.core.currentPosition
import com.egoriku.grodnoroads.maps.compose.core.updatePosition
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.maps.compose.marker.Anchor
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import com.egoriku.grodnoroads.maps.compose.marker.compose.rememberSimpleMarker
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater
import com.egoriku.grodnoroads.maps.compose.util.LatLngInterpolator

const val ANIMATE_DISTANCE_THRESHOLD = 300

@Composable
fun MapUpdater.NavigationMarker(
    position: LatLng,
    icon: () -> MarkerImage
) {
    val marker = rememberSimpleMarker(
        mapUpdater = this,
        markerOptions = {
            MarkerOptions(
                position = position,
                icon = icon(),
                anchor = Anchor(0.5f, 0.5f)
            )
        }
    )

    val progress = remember { Animatable(1f) }
    var animStart by rememberMutableState { position }
    var animEnd by rememberMutableState { position }

    LaunchedEffect(position, marker) {
        if (marker == null) return@LaunchedEffect

        if (marker.currentPosition roundDistanceTo position > ANIMATE_DISTANCE_THRESHOLD) {
            animStart = position
            animEnd = position
            marker.updatePosition(position)
            progress.snapTo(1f)
        } else {
            animStart = marker.currentPosition
            animEnd = position
            progress.snapTo(0f)
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 1400,
                    easing = LinearEasing
                )
            )
        }
    }

    LaunchedEffect(marker) {
        if (marker == null) return@LaunchedEffect

        snapshotFlow { progress.value }
            .collect { fraction ->
                marker.updatePosition(LatLngInterpolator.interpolate(fraction, animStart, animEnd))
            }
    }
}
