package com.egoriku.grodnoroads.map.google.util

import android.Manifest
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.foundation.core.rememberMutableIntState
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.map.R
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation.Companion.UNKNOWN_LOCATION
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.maps.compose.MapProperties
import com.egoriku.grodnoroads.maps.compose.MapType
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.Style
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.MapStyleOptions

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberMapProperties(
    latLng: StableLatLng,
    mapConfig: MapConfig,
    appMode: AppMode
): MapProperties {
    val context = LocalContext.current
    val isLight = MaterialTheme.colorScheme.isLight

    var mapProperties by rememberMutableState {
        MapProperties(
            mapType = MapType.NORMAL,
            minZoomPreference = 6.0f,
            maxZoomPreference = 17.5f,
        )
    }

    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val mapStyle by rememberMutableIntState(mapConfig.googleMapStyle, isLight) {
        when (mapConfig.googleMapStyle) {
            Style.Minimal -> {
                when {
                    isLight -> R.raw.map_style_light_minimal
                    else -> R.raw.map_style_dark_minimal
                }
            }
            Style.Detailed -> {
                when {
                    isLight -> R.raw.map_style_light_detailed
                    else -> R.raw.map_style_dark_detailed
                }
            }
            Style.Unknown -> error("googleMap style can't be unknown")
        }
    }

    LaunchedEffect(latLng, appMode) {
        mapProperties = mapProperties.copy(
            isMyLocationEnabled = permissionsState.allPermissionsGranted && latLng != UNKNOWN_LOCATION && appMode != AppMode.Drive,
        )
    }

    LaunchedEffect(mapConfig) {
        mapProperties = mapProperties.copy(isTrafficEnabled = mapConfig.trafficJanOnMap)
    }

    LaunchedEffect(mapStyle) {
        mapProperties = mapProperties.copy(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapStyle),
        )
    }

    return mapProperties
}
