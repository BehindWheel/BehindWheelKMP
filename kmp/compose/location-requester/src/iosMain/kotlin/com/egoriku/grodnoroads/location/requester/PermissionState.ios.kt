package com.egoriku.grodnoroads.location.requester

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse

@Composable
actual fun rememberLocationPermissionsState(): PermissionState {
    var permissionState by rememberMutableState { PermissionState(allPermissionsGranted = false) }

    SideEffect {
        permissionState = permissionState.copy(allPermissionsGranted = hasLocationPermissions())
    }

    return permissionState
}

fun hasLocationPermissions(): Boolean {
    val status: CLAuthorizationStatus = CLLocationManager.authorizationStatus()
    return status == kCLAuthorizationStatusAuthorizedAlways || status == kCLAuthorizationStatusAuthorizedWhenInUse
}