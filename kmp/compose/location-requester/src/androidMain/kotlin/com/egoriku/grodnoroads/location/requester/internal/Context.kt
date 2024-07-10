package com.egoriku.grodnoroads.location.requester.internal

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

internal fun Context.hasLocationPermissions() = hasPermissions(LOCATION_PERMISSIONS)

private fun Context.hasPermissions(permissions: Array<String>) =
    permissions.all { hasPermission(it) }

private fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
