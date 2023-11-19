package com.egoriku.grodnoroads.location.permissions.internal

import android.Manifest
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

internal typealias PermissionRequester = ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>

internal val LOCATION_PERMISSIONS = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

@Composable
internal fun rememberPermissionRequester(
    onResult: (Map<String, Boolean>) -> Unit
): PermissionRequester = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestMultiplePermissions(),
    onResult = onResult
)
