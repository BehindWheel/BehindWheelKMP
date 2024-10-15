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
import com.egoriku.grodnoroads.maps.compose.configuration.MapColor
import com.egoriku.grodnoroads.maps.compose.configuration.MapProperties
import com.egoriku.grodnoroads.maps.compose.configuration.MapType

@Composable
fun rememberMapProperties(
    mapConfig: MapConfig,
    appMode: AppMode,
    isRequestCurrentLocation: Boolean
): MapProperties {
    val isLight = MaterialTheme.colorScheme.isLight

    var mapProperties by rememberMutableState(isLight, mapConfig) {
        MapProperties(
            mapType = MapType.Normal,
            minZoomPreference = 6.0f,
            maxZoomPreference = 17.5f,
            isTrafficEnabled = mapConfig.trafficJanOnMap,
            mapColor = if (isLight) MapColor.Light else MapColor.Dark
        )
    }

    val permissionsState = rememberLocationPermissionsState()

    LaunchedEffect(appMode, isRequestCurrentLocation) {
        mapProperties = mapProperties.copy(
            isMyLocationEnabled = permissionsState.allPermissionsGranted &&
                appMode != AppMode.Drive
        )
    }

    return mapProperties
}
