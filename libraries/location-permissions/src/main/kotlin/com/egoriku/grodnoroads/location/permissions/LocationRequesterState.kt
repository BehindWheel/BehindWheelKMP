package com.egoriku.grodnoroads.location.permissions

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.location.permissions.internal.LOCATION_PERMISSIONS
import com.egoriku.grodnoroads.location.permissions.internal.PermissionRequester

@Stable
class LocationRequesterState {
    internal var permissionRequester: PermissionRequester? = null

    fun launchRequest() {
        permissionRequester?.launch(LOCATION_PERMISSIONS)
    }
}
