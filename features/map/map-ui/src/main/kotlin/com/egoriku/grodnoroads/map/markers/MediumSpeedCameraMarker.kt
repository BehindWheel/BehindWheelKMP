package com.egoriku.grodnoroads.map.markers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.MediumSpeedCamera
import com.egoriku.grodnoroads.map.foundation.SpeedLimitSign
import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MediumSpeedCameraMarker(
    mediumSpeedCamera: MediumSpeedCamera,
    onFromCache: (Int) -> BitmapDescriptor
) {
    // https://github.com/googlemaps/android-maps-compose/issues/46
    val markerState = rememberMarkerState(position = mediumSpeedCamera.position)

    LaunchedEffect(key1 = mediumSpeedCamera.position) {
        markerState.position = mediumSpeedCamera.position
    }

    MarkerInfoWindow(
        state = markerState,
        icon = onFromCache(R.drawable.ic_map_medium_speed_camera)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(8.dp)
        ) {
            Text(
                text = mediumSpeedCamera.name,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            SpeedLimitSign(limit = mediumSpeedCamera.speedCar)
        }
    }
}