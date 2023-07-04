package com.egoriku.grodnoroads.map.foundation.map.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.CameraPositionState
import java.util.Locale

@Composable
fun DebugView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState
) {
    Column(
        modifier
            .statusBarsPadding()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val target = cameraPositionState.position.target
        val formattedLat = String.format(Locale.US, "%.5f", target.latitude)
        val formattedLng = String.format(Locale.US, "%.5f", target.longitude)
        Text(text = "Target: $formattedLat, $formattedLng")
        Text(text = "Zoom: ${cameraPositionState.position.zoom}")
        Text(text = "Tilt: ${cameraPositionState.position.tilt}")
        Text(text = "Bearing: ${cameraPositionState.position.bearing}")
    }
}