package com.egoriku.grodnoroads.guidance.screen.util

import android.content.Context
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
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.StringRes
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.ActionMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.SimpleMessage
import com.egoriku.grodnoroads.extensions.openAppSettings
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus.FineLocationDenied
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus.GmsLocationDisabled
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus.GmsLocationEnabled
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus.PermissionDenied
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus.ShowRationale

class SnackbarMessageBuilder(private val context: Context) {

    fun handleDriveModeRequest(status: LocationRequestStatus): SnackbarMessage? = when (status) {
        ShowRationale -> SimpleMessage(
            title = StringRes(Res.string.snackbar_drive_mode_rationale_title),
            description = StringRes(Res.string.snackbar_drive_mode_rationale_description)
        )
        FineLocationDenied -> SimpleMessage(
            title = StringRes(Res.string.snackbar_drive_mode_fine_location_denied)
        )
        PermissionDenied -> ActionMessage(
            title = StringRes(Res.string.snackbar_drive_mode_permission_denied),
            onAction = {
                context.openAppSettings()
            }
        )
        GmsLocationDisabled -> SimpleMessage(
            title = StringRes(Res.string.snackbar_drive_mode_gms_disabled_title),
            description = StringRes(Res.string.snackbar_drive_mode_gms_disabled_description)
        )
        GmsLocationEnabled -> null
    }

    fun handleCurrentLocationRequest(status: LocationRequestStatus): SnackbarMessage? =
        when (status) {
            ShowRationale -> SimpleMessage(title = StringRes(Res.string.snackbar_current_location_rationale_title))
            FineLocationDenied -> SimpleMessage(title = StringRes(Res.string.snackbar_current_location_fine_location_denied))
            PermissionDenied -> ActionMessage(
                title = StringRes(Res.string.snackbar_current_location_permission_denied),
                onAction = {
                    context.openAppSettings()
                }
            )
            GmsLocationDisabled -> SimpleMessage(
                title = StringRes(Res.string.snackbar_current_location_gms_disabled_title),
                description = StringRes(Res.string.snackbar_current_location_gms_disabled_description)
            )
            GmsLocationEnabled -> null
        }
}