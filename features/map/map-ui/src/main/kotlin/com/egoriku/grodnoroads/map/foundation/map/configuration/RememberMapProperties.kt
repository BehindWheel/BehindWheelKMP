package com.egoriku.grodnoroads.map.foundation.map.configuration

import android.Manifest
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.map.R
import com.egoriku.grodnoroads.map.domain.model.LocationState
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.Style
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberMapProperties(locationState: LocationState, mapConfig: MapConfig): MapProperties {
    val context = LocalContext.current

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    )

    val mapStyle = when (mapConfig.googleMapStyle) {
        Style.Minimal -> {
            when {
                MaterialTheme.colors.isLight -> R.raw.map_style_light_minimal
                else -> R.raw.map_style_dark_minimal
            }
        }
        Style.Detailed -> {
            when {
                MaterialTheme.colors.isLight -> R.raw.map_style_light_detailed
                else -> R.raw.map_style_dark_detailed
            }
        }
        Style.Unknown -> error("googleMap style can't be unknown")
    }

    val mapProperties by remember(locationState, mapStyle, mapConfig) {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = locationPermissionsState.allPermissionsGranted && locationState == LocationState.None,
                mapType = MapType.NORMAL,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapStyle),
                minZoomPreference = 7.0f,
                maxZoomPreference = 17.5f,
                isTrafficEnabled = mapConfig.trafficJanOnMap,
            )
        )
    }
    return mapProperties
}