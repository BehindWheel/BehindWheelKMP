package com.egoriku.grodnoroads.map.google.markers

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.ui.IconGenerator

@Composable
fun MapUpdater.ReportsMarker(
    position: StableLatLng,
    message: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val iconGenerator by remember { mutableStateOf(IconGenerator(context)) }
    val icon by remember(message) {
        mutableStateOf(
            BitmapDescriptorFactory.fromBitmap(
                iconGenerator.makeIcon(message)
            )
        )
    }

    DisposableEffect(message, position) {
        onDispose {
            removeMarker(position.value)
        }
    }

    LaunchedEffect(message, position) {
        addMarker(
            position = position.value,
            icon = icon,
            zIndex = 2.0f,
            onClick = onClick,
            title = message
        )
    }
}