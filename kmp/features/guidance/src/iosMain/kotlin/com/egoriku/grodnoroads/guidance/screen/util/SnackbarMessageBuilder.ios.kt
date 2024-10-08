package com.egoriku.grodnoroads.guidance.screen.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.snackbar_drive_mode_permission_denied
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.extensions.openAppSettings
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus

private class SnackbarMessageBuilderIos : SnackbarMessageBuilder {
    override fun handleDriveModeRequest(locationRequestStatus: LocationRequestStatus): SnackbarMessage? {
        return when (locationRequestStatus) {
            LocationRequestStatus.FineLocationDenied -> null
            LocationRequestStatus.GmsLocationDisabled -> null
            LocationRequestStatus.GmsLocationEnabled -> null
            LocationRequestStatus.PermissionDenied -> {
                SnackbarMessage.ActionMessage(
                    title = MessageData.StringRes(Res.string.snackbar_drive_mode_permission_denied),
                    onAction = {
                        openAppSettings()
                    }
                )
            }
            LocationRequestStatus.ShowRationale -> null
        }
    }

    override fun handleCurrentLocationRequest(locationRequestStatus: LocationRequestStatus): SnackbarMessage? {
        return null
    }
}

@Composable
actual fun rememberSnackbarMessageBuilder(): SnackbarMessageBuilder {
    return remember { SnackbarMessageBuilderIos() }
}
