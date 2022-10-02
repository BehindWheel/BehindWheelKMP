package com.egoriku.grodnoroads.screen.map.ui.markers

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.ui.IconGenerator

@Composable
fun ReportsMarker(reports: MapEvent.Reports, onMarkerClick: (MapEvent.Reports) -> Unit) {
    val context = LocalContext.current
    val iconGenerator by remember { mutableStateOf(IconGenerator(context)) }

    // https://github.com/googlemaps/android-maps-compose/issues/46
    val markerState = rememberMarkerState(position = reports.position)

    val icon by remember(reports) {
        mutableStateOf(
            BitmapDescriptorFactory.fromBitmap(
                iconGenerator.makeIcon(reports.markerMessage)
            )
        )
    }

    LaunchedEffect(key1 = reports.position) {
        markerState.position = reports.position
    }

    Marker(
        state = markerState,
        icon = icon,
        zIndex = 2f,
        onClick = {
            onMarkerClick(reports)
            true
        }
    )
}