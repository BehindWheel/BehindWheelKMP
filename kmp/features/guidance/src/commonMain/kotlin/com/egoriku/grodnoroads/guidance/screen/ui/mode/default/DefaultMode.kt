package com.egoriku.grodnoroads.guidance.screen.ui.mode.default

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Arrow
import com.egoriku.grodnoroads.foundation.icons.outlined.PinLocation
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import com.egoriku.grodnoroads.location.requester.WithLocationRequester
import com.egoriku.grodnoroads.location.requester.rememberLocationRequesterState

@Composable
fun DefaultMode(
    onLocationRequestStateChange: (LocationRequestStatus) -> Unit,
    modifier: Modifier = Modifier,
    openReportFlow: () -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PrimaryInverseCircleButton(
                size = Large,
                onClick = openReportFlow
            ) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.PinLocation,
                    contentDescription = null
                )
            }

            val locationRequesterState = rememberLocationRequesterState()
            WithLocationRequester(
                locationRequesterState = locationRequesterState,
                onStateChange = onLocationRequestStateChange
            ) {
                PrimaryCircleButton(
                    size = Large,
                    onClick = locationRequesterState::launchRequest
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = GrodnoRoads.Outlined.Arrow,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DefaultModePreview() = GrodnoRoadsM3ThemePreview {
    DefaultMode(
        onLocationRequestStateChange = {},
        openReportFlow = {}
    )
}
