package com.egoriku.grodnoroads.location.requester

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberLocationPermissionsState(): PermissionState {
    // TODO: check how it works on ios
    return remember { PermissionState(allPermissionsGranted = true) }
}