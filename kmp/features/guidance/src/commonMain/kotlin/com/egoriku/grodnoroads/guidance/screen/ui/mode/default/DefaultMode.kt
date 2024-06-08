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
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_arrow
import com.egoriku.grodnoroads.compose.resources.ic_pin_location
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import com.egoriku.grodnoroads.location.requester.WithLocationRequester
import com.egoriku.grodnoroads.location.requester.rememberLocationRequesterState
import org.jetbrains.compose.resources.painterResource

@Composable
fun DefaultMode(
    onLocationRequestStateChanged: (LocationRequestStatus) -> Unit,
    openReportFlow: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PrimaryInverseCircleButton(
                size = Large,
                onClick = openReportFlow
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_pin_location),
                    contentDescription = null
                )
            }

            val locationRequesterState = rememberLocationRequesterState()
            WithLocationRequester(
                locationRequesterState = locationRequesterState,
                onStateChanged = onLocationRequestStateChanged
            ) {
                PrimaryCircleButton(
                    size = Large,
                    onClick = locationRequesterState::launchRequest,
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.ic_arrow),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun DefaultModePreview() = GrodnoRoadsM3ThemePreview {
    DefaultMode({}, {})
}