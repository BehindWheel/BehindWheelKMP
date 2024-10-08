package com.egoriku.grodnoroads.location.requester

import androidx.compose.runtime.Composable

data class PermissionState(val allPermissionsGranted: Boolean)

@Composable
expect fun rememberLocationPermissionsState(): PermissionState
