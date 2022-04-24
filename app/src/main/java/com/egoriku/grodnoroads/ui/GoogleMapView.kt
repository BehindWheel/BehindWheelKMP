package com.egoriku.grodnoroads.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.MarkerCache
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.extension.logD
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.*
import org.koin.androidx.compose.get

private val grodnoPosition = LatLng(53.6687765, 23.8212226)
private val defaultCameraPosition = CameraPosition.fromLatLngZoom(grodnoPosition, 13f)

@Composable
fun GoogleMapView(
    modifier: Modifier,
    stationary: List<Camera>
) {
    val context = LocalContext.current
    val markerCache = get<MarkerCache>()

    val cameraPositionState = rememberCameraPositionState {
        position = defaultCameraPosition
    }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = false)
        )
    }
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
            )
        )
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onPOIClick = {
            logD("POI clicked: ${it.name}")
        }
    ) {
        stationary.forEach { camera ->
            MarkerInfoWindow(
                state = rememberMarkerState(position = camera.position),
                icon = markerCache.getOrPut(id = R.drawable.ic_speed_camera, size = 80),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                        .padding(8.dp)
                ) {
                    Text(text = camera.message, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    SpeedLimitSign(limit = camera.speed)
                }
            }
        }
    }

    // DebugView(cameraPositionState)
}