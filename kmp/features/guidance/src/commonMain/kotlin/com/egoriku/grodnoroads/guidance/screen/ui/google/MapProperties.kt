package com.egoriku.grodnoroads.guidance.screen.ui.google

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.guidance.domain.model.MapConfig
import com.egoriku.grodnoroads.location.requester.rememberLocationPermissionsState
import com.egoriku.grodnoroads.maps.compose.configuration.MapProperties
import com.egoriku.grodnoroads.maps.compose.configuration.MapType
import com.egoriku.grodnoroads.maps.compose.style.rememberMapStyleLoader
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.Style

@Composable
fun rememberMapProperties(
    mapConfig: MapConfig,
    appMode: AppMode,
    isRequestCurrentLocation: Boolean
): MapProperties {
    val mapStyleLoader = rememberMapStyleLoader()
    val isLight = MaterialTheme.colorScheme.isLight

    var mapProperties by rememberMutableState {
        MapProperties(
            mapType = MapType.Normal,
            minZoomPreference = 6.0f,
            maxZoomPreference = 17.5f,
        )
    }

    val permissionsState = rememberLocationPermissionsState()
    val mapStyle by rememberMutableState(mapConfig.googleMapStyle, isLight) {
        when (mapConfig.googleMapStyle) {
            Style.Minimal -> {
                when {
                    isLight -> "files/map_style_light_minimal.json"
                    else -> "files/map_style_dark_minimal.json"
                }
            }
            Style.Detailed -> {
                when {
                    isLight -> "files/map_style_light_detailed.json"
                    else -> "files/map_style_dark_detailed.json"
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
            mapStyleOptions = mapStyleLoader.load(mapStyle),
        )
    }

    return mapProperties
}