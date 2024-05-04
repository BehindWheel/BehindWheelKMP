package com.egoriku.grodnoroads.guidance.screen.ui.google.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.foundation.core.rememberMutableIntState
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.guidance.domain.model.MapConfig
import com.egoriku.grodnoroads.location.requester.rememberLocationPermissionsState
import com.egoriku.grodnoroads.maps.compose.MapProperties
import com.egoriku.grodnoroads.maps.compose.MapType
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.Style
import com.egoriku.grodnoroads.shared.resources.MR
import com.google.android.gms.maps.model.MapStyleOptions

@Composable
fun rememberMapProperties(
    mapConfig: MapConfig,
    appMode: AppMode,
    isRequestCurrentLocation: Boolean
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

    val permissionsState = rememberLocationPermissionsState()
    val mapStyle by rememberMutableIntState(mapConfig.googleMapStyle, isLight) {
        when (mapConfig.googleMapStyle) {
            Style.Minimal -> {
                when {
                    isLight -> MR.files.map_style_light_minimal_json.rawResId
                    else -> MR.files.map_style_dark_minimal_json.rawResId
                }
            }
            Style.Detailed -> {
                when {
                    isLight -> MR.files.map_style_light_detailed_json.rawResId
                    else -> MR.files.map_style_dark_detailed_json.rawResId
                }
            }
            Style.Unknown -> error("googleMap style can't be unknown")
        }
    }

    LaunchedEffect(appMode, isRequestCurrentLocation) {
        mapProperties = mapProperties.copy(
            isMyLocationEnabled = permissionsState.allPermissionsGranted &&
                    appMode != AppMode.Drive
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
