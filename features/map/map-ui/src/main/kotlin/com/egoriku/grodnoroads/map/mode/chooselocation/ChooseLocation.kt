package com.egoriku.grodnoroads.map.mode.chooselocation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.alignment.OffsetAlignment
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Large
import com.egoriku.grodnoroads.map.mode.chooselocation.component.PinMarker

@Composable
fun ChooseLocation(
    isCameraMoving: Boolean,
    isChooseInDriveMode: Boolean,
    onCancel: () -> Unit,
    onLocationSelected: (Offset) -> Unit
) {
    var markerOffset = remember { Offset.Zero }

    val offsetAlignment = remember {
        if (isChooseInDriveMode) {
            OffsetAlignment(xOffset = 0.5f, yOffset = 0.7f)
        } else {
            OffsetAlignment(xOffset = 0.5f, yOffset = 0.5f)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PinMarker(
            modifier = Modifier.align(offsetAlignment),
            animate = isCameraMoving,
            onGloballyPositioned = {
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
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
            PrimaryCircleButton(
                size = Large,
                onClick = { onLocationSelected(markerOffset) },
                enabled = !isCameraMoving,
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun ChooseLocationPreview() = GrodnoRoadsM3ThemePreview {
    ChooseLocation(
        isCameraMoving = false,
        isChooseInDriveMode = true,
        onCancel = {},
        onLocationSelected = {}
    )
}