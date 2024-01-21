package com.egoriku.grodnoroads.location.requester.core

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.egoriku.grodnoroads.location.requester.internal.LOCATION_PERMISSIONS

fun Context.hasLocationPermissions() = hasPermissions(LOCATION_PERMISSIONS)

private fun Context.hasPermissions(permissions: Array<String>) =
    permissions.all { hasPermission(it) }

private fun Context.hasPermission(permission: String) =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
