package com.egoriku.grodnoroads.map.mode.chooselocation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.CircleButton
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.map.mode.chooselocation.component.PinMarker

@Composable
fun ChooseLocation(
    isCameraMoving: Boolean,
    onCancel: () -> Unit,
    onLocationSelected: (Offset) -> Unit
) {
    var markerPosition by rememberMutableState { Offset.Zero }

    Box(modifier = Modifier.fillMaxSize()) {
        PinMarker(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 60.dp),
            animate = isCameraMoving,
            onGloballyPositioned = {
                markerPosition = it
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CircleButton(onClick = onCancel) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier)
            CircleButton(
                onClick = { onLocationSelected(markerPosition) },
                enabled = !isCameraMoving
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
private fun ChooseLocationPreview() {
    GrodnoRoadsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ChooseLocation(
                isCameraMoving = false,
                onCancel = {},
                onLocationSelected = {}
            )
        }
    }
}