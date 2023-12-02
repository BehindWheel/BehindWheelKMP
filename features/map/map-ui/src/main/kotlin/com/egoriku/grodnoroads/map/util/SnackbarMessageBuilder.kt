package com.egoriku.grodnoroads.map.util

import android.content.Context
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Raw
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.ActionMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.SimpleMessage
import com.egoriku.grodnoroads.extensions.openAppSettings
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus.*

class SnackbarMessageBuilder(private val context: Context) {

    fun handleDriveModeRequest(status: LocationRequestStatus): SnackbarMessage? = when (status) {
        ShowRationale -> SimpleMessage(
            title = Raw("Разрешение для доступа к геолокации отклонено"),
            description = Raw("Используется для отображения маркера в режиме навигации")
        )
        FineLocationDenied -> SimpleMessage(
            title = Raw("Для навигации нужен доступ к более точному местоположению")
        )
        PermissionDenied -> ActionMessage(
            title = Raw("Доступ к геолокации запрещен. Вы можете дать разрешение в настройках"),
            onAction = {
                context.openAppSettings()
            }
        )
        GmsLocationDisabled -> SimpleMessage(
            title = Raw("Для навигации нужен доступ к геолокации"),
            description = Raw("Ее можно включить самостоятельно в панели уведомлений")
        )
        GmsLocationEnabled -> null
    }

    fun handleCurrentLocationRequest(status: LocationRequestStatus): SnackbarMessage? =
        when (status) {
            ShowRationale -> SimpleMessage(title = Raw("Разрешение для доступа к геолокации отклонено"))
            FineLocationDenied -> SimpleMessage(title = Raw("Нужен доступ к более точному местоположению"))
            PermissionDenied -> ActionMessage(
                title = Raw("Доступ к геолокации запрещен. Вы можете дать разрешение в настройках"),
                onAction = {
                    context.openAppSettings()
                }
            )
            GmsLocationDisabled -> SimpleMessage(
                title = Raw("Доступ к геолокации отклонен"),
                description = Raw("Ее можно включить самостоятельно в панели уведомлений")
            )
            GmsLocationEnabled -> null
        }
}