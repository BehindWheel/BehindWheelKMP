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
import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.foundation.SpeedLimitSign
import com.egoriku.grodnoroads.foundation.map.rememberCameraPositionValues
import com.egoriku.grodnoroads.foundation.map.rememberMapProperties
import com.egoriku.grodnoroads.foundation.map.rememberUiSettings
import com.egoriku.grodnoroads.screen.map.domain.MapEvent
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.*
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
    mapEvents: List<MapEvent>,
    locationState: LocationState,
    onMarkerClick: (Reports) -> Unit
) {
    val markerCache = get<MarkerCache>()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(grodnoPosition, 12.5f)
    }

    val cameraPositionValues = rememberCameraPositionValues(cameraPositionState, locationState)

    LaunchedEffect(key1 = locationState) {
        if (locationState != LocationState.None) {
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
        properties = rememberMapProperties(locationState),
        uiSettings = rememberUiSettings(),
        contentPadding = WindowInsets.statusBars.asPaddingValues()
    ) {
        mapEvents.forEach { mapEvent ->
            when (mapEvent) {
                is StationaryCamera -> PlaceStationaryCamera(mapEvent, markerCache)
                is Reports -> PlaceReports(mapEvent, onMarkerClick)
                is MobileCamera -> PlaceMobileCameras(mapEvent, markerCache)
            }
        }

        if (locationState != LocationState.None) {
            Marker(
                state = MarkerState(position = locationState.latLng),
                icon = markerCache.getVector(id = R.drawable.ic_arrow),
                rotation = cameraPositionValues.markerRotation,
                anchor = Offset(0.5f, 0.5f),
                zIndex = 1f
            )
        }
    }
}

@Composable
fun PlaceReports(reports: Reports, onMarkerClick: (Reports) -> Unit) {
    val context = LocalContext.current
    val iconGenerator by remember { mutableStateOf(IconGenerator(context)) }

    // https://github.com/googlemaps/android-maps-compose/issues/46
    val marketState = rememberMarkerState(position = reports.position)

    val icon by remember(reports) {
        mutableStateOf(
            BitmapDescriptorFactory.fromBitmap(
                iconGenerator.makeIcon(reports.shortMessage)
            )
        )
    }

    LaunchedEffect(key1 = reports.position) {
        marketState.position = reports.position
    }

    Marker(
        state = marketState,
        icon = icon,
        zIndex = 2f,
        onClick = {
            onMarkerClick(reports)
            true
        }
    )
}

@Composable
fun PlaceStationaryCamera(
    stationaryCamera: StationaryCamera,
    markerCache: MarkerCache
) {
    MarkerInfoWindow(
        state = rememberMarkerState(position = stationaryCamera.position),
        icon = markerCache.getVector(id = R.drawable.ic_stationary_camera),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .padding(8.dp)
        ) {
            Text(
                text = stationaryCamera.message,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            SpeedLimitSign(limit = stationaryCamera.speed)
        }
    }
}

@Composable
fun PlaceMobileCameras(mobileCamera: MobileCamera, markerCache: MarkerCache) {
    MarkerInfoWindow(
        state = rememberMarkerState(position = mobileCamera.position),
        icon = markerCache.getVector(id = R.drawable.ic_mobile_camera)
    )
}