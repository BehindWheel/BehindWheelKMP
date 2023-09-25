package com.egoriku.grodnoroads.map.markers

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.mapswrapper.MapScope
import com.egoriku.grodnoroads.mapswrapper.core.StableLatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.ui.IconGenerator

@Composable
fun MapScope.ReportsMarker(
    latLng: StableLatLng,
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

    DisposableEffect(message, latLng) {
        onDispose {
            removeMarker(latLng.value)
        }
    }

    addMarker(
        position = latLng.value,
        icon = icon,
        zIndex = 1.0f,
        onClick = onClick
    )
}