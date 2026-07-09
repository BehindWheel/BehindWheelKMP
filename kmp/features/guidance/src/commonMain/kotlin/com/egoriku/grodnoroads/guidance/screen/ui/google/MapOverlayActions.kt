package com.egoriku.grodnoroads.guidance.screen.ui.google

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Add
import com.egoriku.grodnoroads.foundation.icons.outlined.Compass
import com.egoriku.grodnoroads.foundation.icons.outlined.Geo
import com.egoriku.grodnoroads.foundation.icons.outlined.Remove
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButton
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButtonGroup
import com.egoriku.grodnoroads.foundation.uikit.button.ActionIcon
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import com.egoriku.grodnoroads.location.requester.WithLocationRequester
import com.egoriku.grodnoroads.location.requester.rememberLocationRequesterState
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay

@Composable
fun MapOverlayActions(
    zoomIn: () -> Unit,
    zoomOut: () -> Unit,
    rotateToNorth: () -> Unit,
    bearing: Float,
    appMode: AppMode,
    modifier: Modifier = Modifier,
    onLocationRequestStateChange: (LocationRequestStatus) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ActionButtonGroup {
            ActionIcon(imageVector = GrodnoRoads.Outlined.Add, onClick = zoomIn)
            ActionIcon(imageVector = GrodnoRoads.Outlined.Remove, onClick = zoomOut)
        }

        val locationRequesterState = rememberLocationRequesterState()
        WithLocationRequester(
            locationRequesterState = locationRequesterState,
            onStateChange = onLocationRequestStateChange
        ) {
            ActionButton(
                onClick = locationRequesterState::launchRequest,
                imageVector = GrodnoRoads.Outlined.Geo
            )
        }

        val shouldShowCompass = shouldShowCompass(appMode, bearing)
        ActionButton(
            onClick = rotateToNorth,
            imageVector = GrodnoRoads.Outlined.Compass,
            enabled = shouldShowCompass,
            modifier = Modifier.graphicsLayer {
                alpha = if (shouldShowCompass) 1f else 0f
            }
        )
    }
}

private fun shouldShowCompass(
    appMode: AppMode,
    bearing: Float
): Boolean {
    return (appMode == AppMode.Default || appMode == AppMode.ChooseLocation) && bearing != 0f
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun MapOverlayActionsPreview() = GrodnoRoadsM3ThemePreview {
    var bearing by rememberMutableState { 190f }

    LaunchedEffect(bearing) {
        delay(3.seconds)
        bearing = Random.nextDouble(0.0, 359.0).toFloat()
    }

    MapOverlayActions(
        modifier = Modifier.padding(32.dp),
        zoomIn = {},
        zoomOut = {},
        rotateToNorth = { bearing = 0f },
        bearing = bearing,
        appMode = AppMode.Default,
        onLocationRequestStateChange = {}
    )
}
