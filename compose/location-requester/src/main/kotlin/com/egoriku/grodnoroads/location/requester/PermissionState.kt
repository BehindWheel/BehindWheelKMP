package com.egoriku.grodnoroads.location.requester

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.location.requester.core.hasLocationPermissions

data class PermissionState(val allPermissionsGranted: Boolean)

@Composable
fun rememberLocationPermissionsState(): PermissionState {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var permissionState by rememberMutableState { PermissionState(allPermissionsGranted = false) }

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            permissionState = permissionState.copy(
                allPermissionsGranted = context.hasLocationPermissions()
            )
        }
    }

    return permissionState
}