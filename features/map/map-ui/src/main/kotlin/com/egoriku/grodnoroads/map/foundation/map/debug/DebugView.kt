package com.egoriku.grodnoroads.map.foundation.map.debug

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.CameraPositionState

@Composable
fun DebugView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState
) {
    Column(
        modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Camera position is ${cameraPositionState.position}")
        Spacer(modifier = Modifier.height(4.dp))
    }
}