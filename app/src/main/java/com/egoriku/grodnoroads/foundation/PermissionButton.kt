package com.egoriku.grodnoroads.foundation

import android.Manifest
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.util.gms.rememberGmsLocationPermissionsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionButton(
    modifier: Modifier = Modifier,
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit,
    content: @Composable () -> Unit
) {
    val gmsLocationPermissionsState = rememberGmsLocationPermissionsState(
        onAccepted = onLocationEnabled,
        onDenied = onLocationDisabled
    )

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    ) { permissions ->
        if (permissions.values.none { !it }) {
            gmsLocationPermissionsState.launchLocationRequest()
        }
    }

    IconButton(
        onClick = {
            locationPermissionsState.launchMultiplePermissionRequest()
        },
        modifier = modifier,
        content = content
    )
}