package com.egoriku.grodnoroads.screen.map.ui

import android.graphics.Point
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.MapEvent
import com.egoriku.grodnoroads.domain.model.UserPosition
import com.egoriku.grodnoroads.foundation.SpeedLimitSign
import com.egoriku.grodnoroads.foundation.map.rememberMapProperties
import com.egoriku.grodnoroads.util.MarkerCache
import com.egoriku.grodnoroads.util.SphericalUtil
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
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val markerCache = get<MarkerCache>()

    val iconGenerator by remember { mutableStateOf(IconGenerator(context)) }

    var lastBearing by remember { mutableStateOf(0.0f) }
    var directionBearing by remember { mutableStateOf(0.0f) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(grodnoPosition, 12.5f)
    }

    val screenLocation =
        cameraPositionState.projection?.toScreenLocation(userPosition.latLng)?.apply {
            set(x, y - screenHeight / 3)
        } ?: Point()

    val fromScreenLocation = cameraPositionState.projection?.fromScreenLocation(screenLocation)
        ?: userPosition.latLng

    lastBearing = directionBearing
    directionBearing = userPosition.bearing

    if (directionBearing == 0.0f) {
        directionBearing = lastBearing
    }

    val computeHeading = SphericalUtil.computeHeading(userPosition.latLng, fromScreenLocation)

    LaunchedEffect(key1 = userPosition) {
        if (userPosition != UserPosition.None) {
            val cameraPosition = CameraPosition.Builder()
                .target(fromScreenLocation)
                .zoom(14f)
                .bearing(directionBearing)
                .tilt(25.0f)
                .build()

            cameraPositionState.animate(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = false,
                compassEnabled = false,
                myLocationButtonEnabled = false
            )
        )
    }

    val mapProperties = rememberMapProperties(userPosition)

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        if (userPosition != UserPosition.None) {
            Marker(
                state = MarkerState(position = userPosition.latLng),
                icon = markerCache.getOrPut(
                    id = R.drawable.ic_arrow,
                    size = 80
                ),
                rotation = (directionBearing - computeHeading).toFloat(),
                anchor = Offset(0.5f, 0.5f)
            )
        }

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

        userActions.forEach {
            MarkerInfoWindow(
                state = rememberMarkerState(position = it.position),
                icon = BitmapDescriptorFactory.fromBitmap(
                    iconGenerator.makeIcon("${it.time} ${it.message}")
                )
            )
        }
    }

    // DebugView(cameraPositionState)
}