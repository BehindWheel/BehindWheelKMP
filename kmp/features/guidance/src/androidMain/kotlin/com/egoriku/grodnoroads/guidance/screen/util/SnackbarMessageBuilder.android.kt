package com.egoriku.grodnoroads.guidance.screen.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.snackbar_current_location_fine_location_denied
import com.egoriku.grodnoroads.compose.resources.snackbar_current_location_gms_disabled_description
import com.egoriku.grodnoroads.compose.resources.snackbar_current_location_gms_disabled_title
import com.egoriku.grodnoroads.compose.resources.snackbar_current_location_permission_denied
import com.egoriku.grodnoroads.compose.resources.snackbar_current_location_rationale_title
import com.egoriku.grodnoroads.compose.resources.snackbar_drive_mode_fine_location_denied
import com.egoriku.grodnoroads.compose.resources.snackbar_drive_mode_gms_disabled_description
import com.egoriku.grodnoroads.compose.resources.snackbar_drive_mode_gms_disabled_title
import com.egoriku.grodnoroads.compose.resources.snackbar_drive_mode_permission_denied
import com.egoriku.grodnoroads.compose.resources.snackbar_drive_mode_rationale_description
import com.egoriku.grodnoroads.compose.resources.snackbar_drive_mode_rationale_title
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.extensions.openAppSettings
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus

private class SnackbarMessageBuilderAndroid(private val context: Context) : SnackbarMessageBuilder {

    override fun handleDriveModeRequest(locationRequestStatus: LocationRequestStatus): SnackbarMessage? =
        when (locationRequestStatus) {
            LocationRequestStatus.ShowRationale -> SnackbarMessage.SimpleMessage(
                title = MessageData.StringRes(Res.string.snackbar_drive_mode_rationale_title),
                description = MessageData.StringRes(Res.string.snackbar_drive_mode_rationale_description)
            )
            LocationRequestStatus.FineLocationDenied -> SnackbarMessage.SimpleMessage(
                title = MessageData.StringRes(Res.string.snackbar_drive_mode_fine_location_denied)
            )
            LocationRequestStatus.PermissionDenied -> SnackbarMessage.ActionMessage(
                title = MessageData.StringRes(Res.string.snackbar_drive_mode_permission_denied),
                onAction = {
                    context.openAppSettings()
                }
            )
            LocationRequestStatus.GmsLocationDisabled -> SnackbarMessage.SimpleMessage(
                title = MessageData.StringRes(Res.string.snackbar_drive_mode_gms_disabled_title),
                description = MessageData.StringRes(Res.string.snackbar_drive_mode_gms_disabled_description)
            )
            LocationRequestStatus.GmsLocationEnabled -> null
        }

    override fun handleCurrentLocationRequest(locationRequestStatus: LocationRequestStatus): SnackbarMessage? =
        when (locationRequestStatus) {
            LocationRequestStatus.ShowRationale -> SnackbarMessage.SimpleMessage(
                title = MessageData.StringRes(
                    Res.string.snackbar_current_location_rationale_title
                )
            )
            LocationRequestStatus.FineLocationDenied -> SnackbarMessage.SimpleMessage(
                title = MessageData.StringRes(
                    Res.string.snackbar_current_location_fine_location_denied
                )
            )
            LocationRequestStatus.PermissionDenied -> SnackbarMessage.ActionMessage(
                title = MessageData.StringRes(Res.string.snackbar_current_location_permission_denied),
                onAction = {
                    context.openAppSettings()
                }
            )
            LocationRequestStatus.GmsLocationDisabled -> SnackbarMessage.SimpleMessage(
                title = MessageData.StringRes(Res.string.snackbar_current_location_gms_disabled_title),
                description = MessageData.StringRes(Res.string.snackbar_current_location_gms_disabled_description)
            )
            LocationRequestStatus.GmsLocationEnabled -> null
        }
}

@Composable
actual fun rememberSnackbarMessageBuilder(): SnackbarMessageBuilder {
    val context = LocalContext.current
    return remember { SnackbarMessageBuilderAndroid(context) }
}
