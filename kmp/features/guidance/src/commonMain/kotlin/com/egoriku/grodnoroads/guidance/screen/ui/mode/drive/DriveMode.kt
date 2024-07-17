package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.PinLocation
import com.egoriku.grodnoroads.foundation.icons.outlined.Undo
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.guidance.screen.ui.KeepScreenOn

@Composable
fun DriveMode(
    back: () -> Unit,
    openChooseLocation: () -> Unit
) {
    KeepScreenOn()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PrimaryInverseCircleButton(size = Large, onClick = openChooseLocation) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.PinLocation,
                    contentDescription = null
                )
            }
            PrimaryCircleButton(size = Large, onClick = back) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Undo,
                    contentDescription = null
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun DriveModePReview() = GrodnoRoadsM3ThemePreview {
    DriveMode(
        back = {},
        openChooseLocation = {}
    )
}