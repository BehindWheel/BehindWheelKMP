package com.egoriku.grodnoroads.foundation.map

import android.Manifest
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.common.datastore.DataFlow.googleMapStyle
import com.egoriku.grodnoroads.common.datastore.DataFlow.trafficJamOnMap
import com.egoriku.grodnoroads.common.datastore.dataStore
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.GoogleMapStyle.Style
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun rememberMapProperties(locationState: LocationState): MapProperties {
    val context = LocalContext.current

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    )

    val prefs by remember { mutableStateOf(runBlocking { context.dataStore.data.first() }) }

    val googleMapStyle by context.googleMapStyle.collectAsState(initial = prefs.googleMapStyle)
    val isTrafficEnabled by context.trafficJamOnMap.collectAsState(initial = prefs.trafficJamOnMap)

    val mapStyle = when (googleMapStyle) {
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
    }

    val mapProperties by remember(locationState, mapStyle, isTrafficEnabled) {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = locationPermissionsState.allPermissionsGranted && locationState == LocationState.None,
                mapType = MapType.NORMAL,
                mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, mapStyle),
                minZoomPreference = 7.0f,
                maxZoomPreference = 17.5f,
                isTrafficEnabled = isTrafficEnabled,
            )
        )
    }
    return mapProperties
}