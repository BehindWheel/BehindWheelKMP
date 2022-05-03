package com.egoriku.grodnoroads.screen.map.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.MapEvent
import com.egoriku.grodnoroads.domain.model.UserPosition
import com.egoriku.grodnoroads.foundation.SpeedLimitSign
import com.egoriku.grodnoroads.foundation.map.rememberCameraPositionValues
import com.egoriku.grodnoroads.foundation.map.rememberMapProperties
import com.egoriku.grodnoroads.foundation.map.rememberUiSettings
import com.egoriku.grodnoroads.util.MarkerCache
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.maps.android.ui.IconGenerator
import org.koin.androidx.compose.get

val grodnoPosition = LatLng(53.6687765, 23.8212226)

@Composable
fun GoogleMapView(
    modifier: Modifier,
    stationary: List<Camera>,
    userPosition: UserPosition,
    userActions: List<MapEvent>
) {
    val markerCache = get<MarkerCache>()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(grodnoPosition, 12.5f)
    }

    val cameraPositionValues = rememberCameraPositionValues(cameraPositionState, userPosition)

    LaunchedEffect(key1 = userPosition) {
        if (userPosition != UserPosition.None) {
            val cameraPosition = CameraPosition.Builder()
                .target(cameraPositionValues.targetLatLng)
                .zoom(14f)
                .bearing(cameraPositionValues.bearing)
                .tilt(25.0f)
                .build()

            cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = rememberMapProperties(userPosition),
        uiSettings = rememberUiSettings(),
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        StationaryCameras(stationary, markerCache)
        PlaceUserActions(userActions)

        if (userPosition != UserPosition.None) {
            Marker(
                state = MarkerState(position = userPosition.latLng),
                icon = markerCache.getOrPut(
                    id = R.drawable.ic_arrow,
                    size = 80
                ),
                rotation = cameraPositionValues.markerRotation,
                anchor = Offset(0.5f, 0.5f)
            )
        }
    }

    // DebugView(cameraPositionState)
}

@Composable
fun PlaceUserActions(userActions: List<MapEvent>) {
    val context = LocalContext.current

    val iconGenerator by remember { mutableStateOf(IconGenerator(context)) }

    userActions.forEach {
        MarkerInfoWindow(
            state = rememberMarkerState(position = it.position),
            icon = BitmapDescriptorFactory.fromBitmap(
                iconGenerator.makeIcon("${it.time} ${it.message}")
            )
        )
    }
}

@Composable
fun StationaryCameras(
    stationary: List<Camera>,
    markerCache: MarkerCache
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
                Text(text = camera.message, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                SpeedLimitSign(limit = camera.speed)
            }
        }
    }
}