package com.egoriku.grodnoroads.location.requester

sealed interface LocationRequestStatus {
    data object ShowRationale : LocationRequestStatus
    data object FineLocationDenied : LocationRequestStatus
    data object PermissionDenied : LocationRequestStatus

    data object GmsLocationEnabled : LocationRequestStatus
    data object GmsLocationDisabled : LocationRequestStatus
}
