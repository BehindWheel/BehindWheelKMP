package com.egoriku.grodnoroads.location.requester

actual sealed interface LocationRequestStatus {
    data object PermissionDenied : LocationRequestStatus
    data object PermissionGranted : LocationRequestStatus
}
