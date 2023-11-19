package com.egoriku.grodnoroads.location.permissions

sealed interface LocationRequestStatus {
    sealed interface Permissions : LocationRequestStatus {
        data object ShowRationale : Permissions
        data object DeniedFineLocation : Permissions
        data object Denied : Permissions
    }

    sealed interface GmsSettings : LocationRequestStatus {
        data object GmsLocationEnabled : GmsSettings
        data object GmsLocationDisabled : GmsSettings
    }
}