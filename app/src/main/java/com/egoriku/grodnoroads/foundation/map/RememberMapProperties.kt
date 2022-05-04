package com.egoriku.grodnoroads.foundation.map

import android.Manifest
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.LocationState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberMapProperties(locationState: LocationState): MapProperties {
    val context = LocalContext.current

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    )

    val mapStyle = when {
        isSystemInDarkTheme() -> R.raw.map_dark_style
        else -> R.raw.map_light_style
    }

    val mapProperties by remember(locationState) {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = locationPermissionsState.allPermissionsGranted && locationState == LocationState.None,
                mapType = MapType.NORMAL,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapStyle)
            )
        )
    }
    return mapProperties
}