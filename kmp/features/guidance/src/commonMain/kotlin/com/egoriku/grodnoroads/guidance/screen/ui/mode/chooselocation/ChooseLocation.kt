package com.egoriku.grodnoroads.guidance.screen.ui.mode.chooselocation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.alignment.OffsetAlignment
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Close
import com.egoriku.grodnoroads.foundation.icons.outlined.Ok
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.guidance.screen.ui.mode.chooselocation.component.PinMarker

@Composable
fun ChooseLocation(
    isCameraMoving: Boolean,
    isChooseInDriveMode: Boolean,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    onLocationSelect: (Offset) -> Unit
) {
    var markerOffset = remember { Offset.Zero }

    val offsetAlignment = remember {
        if (isChooseInDriveMode) {
            OffsetAlignment(xOffset = 0.5f, yOffset = 0.7f)
        } else {
            OffsetAlignment(xOffset = 0.5f, yOffset = 0.5f)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        PinMarker(
            modifier = Modifier.align(offsetAlignment),
            animate = isCameraMoving,
            onGloballyPosition = {
                markerOffset = it
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterHorizontally)
        ) {
            PrimaryInverseCircleButton(size = Large, onClick = onCancel) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Close,
                    contentDescription = null
                )
            }
            PrimaryCircleButton(
                size = Large,
                onClick = {
                    if (!isCameraMoving) {
                        onLocationSelect(markerOffset)
                    }
                }
            ) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.Ok,
                    contentDescription = null
                )
            }
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun ChooseLocationPreview() = GrodnoRoadsM3ThemePreview {
    ChooseLocation(
        isCameraMoving = false,
        isChooseInDriveMode = true,
        onCancel = {},
        onLocationSelect = {}
    )
}
