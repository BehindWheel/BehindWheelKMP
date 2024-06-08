package com.egoriku.grodnoroads.location.requester

import androidx.compose.runtime.Stable

@Stable
actual class LocationRequesterState {

    var locationRequester: LocationRequester? = null

    actual fun launchRequest() {
        locationRequester?.request()
    }
}