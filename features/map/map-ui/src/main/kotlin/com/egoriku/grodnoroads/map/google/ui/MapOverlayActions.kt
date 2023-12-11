package com.egoriku.grodnoroads.map.google.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.ActionButton
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.Switch
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import com.egoriku.grodnoroads.location.requester.WithLocationRequester
import com.egoriku.grodnoroads.location.requester.rememberLocationRequesterState
import com.egoriku.grodnoroads.resources.R

@Composable
fun MapOverlayActions(
    modifier: Modifier = Modifier,
    zoomIn: () -> Unit,
    zoomOut: () -> Unit,
    isLocationButtonEnabled: () -> Boolean,
    onLocationRequestStateChanged: (LocationRequestStatus) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GroupedActionsColumn {
            ActionIcon(imageVector = Icons.Default.Add, onClick = zoomIn)
            ActionIcon(imageVector = Icons.Default.Remove, onClick = zoomOut)
        }
        FadeInOutAnimatedVisibility(visible = isLocationButtonEnabled()) {
            val locationRequesterState = rememberLocationRequesterState()
            WithLocationRequester(
                locationRequesterState = locationRequesterState,
                onStateChanged = onLocationRequestStateChanged
            ) {
                ActionButton(
                    onClick = locationRequesterState::launchRequest,
                    icon = R.drawable.ic_geo,
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun MapOverlayActionsPreview() = GrodnoRoadsM3ThemePreview {
    var enabled by rememberMutableState { true }

    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Switch(checked = enabled, onCheckedChange = { enabled = it })
        VerticalSpacer(dp = 64.dp)
        MapOverlayActions(
            zoomIn = {},
            zoomOut = {},
            isLocationButtonEnabled = { enabled },
            onLocationRequestStateChanged = {},
        )
    }
}