package com.egoriku.grodnoroads.screen.map.ui.markers

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
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.SpeedLimitSign
import com.egoriku.grodnoroads.screen.map.domain.model.MapEvent.MobileCamera
import com.egoriku.grodnoroads.util.MarkerCache
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MobileCameraMarker(mobileCamera: MobileCamera, markerCache: MarkerCache) {
    // https://github.com/googlemaps/android-maps-compose/issues/46
    val markerState = rememberMarkerState(position = mobileCamera.position)

    LaunchedEffect(key1 = mobileCamera.position) {
        markerState.position = mobileCamera.position
    }

    MarkerInfoWindow(
        state = markerState,
        icon = markerCache.getVector(id = R.drawable.ic_mobile_camera)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(8.dp)
        ) {
            Text(
                text = mobileCamera.message,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            SpeedLimitSign(limit = mobileCamera.speed)
        }
    }
}