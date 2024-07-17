package com.egoriku.grodnoroads.guidance.screen.ui.google

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Add
import com.egoriku.grodnoroads.foundation.icons.outlined.Geo
import com.egoriku.grodnoroads.foundation.icons.outlined.Remove
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButton
import com.egoriku.grodnoroads.foundation.uikit.button.ActionButtonGroup
import com.egoriku.grodnoroads.foundation.uikit.button.ActionIcon
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import com.egoriku.grodnoroads.location.requester.WithLocationRequester
import com.egoriku.grodnoroads.location.requester.rememberLocationRequesterState

@Composable
fun MapOverlayActions(
    modifier: Modifier = Modifier,
    zoomIn: () -> Unit,
    zoomOut: () -> Unit,
    onLocationRequestStateChanged: (LocationRequestStatus) -> Unit,
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
            onStateChanged = onLocationRequestStateChanged
        ) {
            ActionButton(
                onClick = locationRequesterState::launchRequest,
                imageVector = GrodnoRoads.Outlined.Geo,
            )
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun MapOverlayActionsPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MapOverlayActions(
            zoomIn = {},
            zoomOut = {},
            onLocationRequestStateChanged = {},
        )
    }
}