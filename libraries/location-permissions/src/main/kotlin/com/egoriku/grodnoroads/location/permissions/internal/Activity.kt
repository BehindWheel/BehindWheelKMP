package com.egoriku.grodnoroads.location.permissions.internal

import android.app.Activity
import androidx.core.app.ActivityCompat

internal fun Activity.shouldShowRationale(permissions: Array<String>) =
    permissions.any { shouldShowRationale(it) }

internal fun Activity.shouldShowRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
}