package com.egoriku.grodnoroads.location.requester

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.location.requester.internal.hasLocationPermissions

@Composable
actual fun rememberLocationPermissionsState(): PermissionState {
    val context = LocalContext.current
    var permissionState by rememberMutableState { PermissionState(allPermissionsGranted = false) }

    SideEffect {
        permissionState = permissionState.copy(allPermissionsGranted = context.hasLocationPermissions())
    }

    return permissionState
}
