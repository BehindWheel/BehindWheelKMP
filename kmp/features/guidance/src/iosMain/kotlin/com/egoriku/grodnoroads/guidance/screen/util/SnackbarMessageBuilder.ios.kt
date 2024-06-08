package com.egoriku.grodnoroads.guidance.screen.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.snackbar_drive_mode_permission_denied
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

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
                        val settingsUrl = NSURL.URLWithString(UIApplicationOpenSettingsURLString)!!
                        UIApplication.sharedApplication.openURL(settingsUrl)
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