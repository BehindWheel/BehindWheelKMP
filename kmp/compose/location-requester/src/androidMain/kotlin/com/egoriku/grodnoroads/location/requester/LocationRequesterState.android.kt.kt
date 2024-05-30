package com.egoriku.grodnoroads.location.requester

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.location.requester.internal.LOCATION_PERMISSIONS
import com.egoriku.grodnoroads.location.requester.internal.PermissionRequester

@Stable
actual class LocationRequesterState {
    internal var permissionRequester: PermissionRequester? = null

    fun launchRequest() {
        permissionRequester?.launch(LOCATION_PERMISSIONS)
    }
}
