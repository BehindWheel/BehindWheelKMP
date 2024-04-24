package com.egoriku.grodnoroads.guidance.screen.util

import android.content.Context
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Resource
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
import com.egoriku.grodnoroads.resources.MR

class SnackbarMessageBuilder(private val context: Context) {

    fun handleDriveModeRequest(status: LocationRequestStatus): SnackbarMessage? = when (status) {
        ShowRationale -> SimpleMessage(
            title = Resource(MR.strings.snackbar_drive_mode_rationale_title.resourceId),
            description = Resource(MR.strings.snackbar_drive_mode_rationale_description.resourceId)
        )
        FineLocationDenied -> SimpleMessage(
            title = Resource(MR.strings.snackbar_drive_mode_fine_location_denied.resourceId)
        )
        PermissionDenied -> ActionMessage(
            title = Resource(MR.strings.snackbar_drive_mode_permission_denied.resourceId),
            onAction = {
                context.openAppSettings()
            }
        )
        GmsLocationDisabled -> SimpleMessage(
            title = Resource(MR.strings.snackbar_drive_mode_gms_disabled_title.resourceId),
            description = Resource(MR.strings.snackbar_drive_mode_gms_disabled_description.resourceId)
        )
        GmsLocationEnabled -> null
    }

    fun handleCurrentLocationRequest(status: LocationRequestStatus): SnackbarMessage? =
        when (status) {
            ShowRationale -> SimpleMessage(title = Resource(MR.strings.snackbar_current_location_rationale_title.resourceId))
            FineLocationDenied -> SimpleMessage(title = Resource(MR.strings.snackbar_current_location_fine_location_denied.resourceId))
            PermissionDenied -> ActionMessage(
                title = Resource(MR.strings.snackbar_current_location_permission_denied.resourceId),
                onAction = {
                    context.openAppSettings()
                }
            )
            GmsLocationDisabled -> SimpleMessage(
                title = Resource(MR.strings.snackbar_current_location_gms_disabled_title.resourceId),
                description = Resource(MR.strings.snackbar_current_location_gms_disabled_description.resourceId)
            )
            GmsLocationEnabled -> null
        }
}