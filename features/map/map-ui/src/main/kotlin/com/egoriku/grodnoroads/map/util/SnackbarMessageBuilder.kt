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

    fun buildMessage(status: LocationRequestStatus): SnackbarMessage? = when (status) {
        GmsLocationDisabled -> SimpleMessage(
            title = Raw("Для навигации нужен доступ к геолокации")
        )
        GmsLocationEnabled -> null
        PermissionDenied -> ActionMessage(
            title = Raw("Доступ к геолокации запрещен. Вы можете дать разрешение в настройках"),
            onAction = {
                context.openAppSettings()
            }
        )
        FineLocationDenied -> SimpleMessage(
            title = Raw("Для навигации нужен доступ к более точному местоположению")
        )
        ShowRationale -> SimpleMessage(
            title = Raw("Разрешение для доступа к геолокации отклонено"),
            description = Raw("Используется для отображения маркера в режиме навигации")
        )
    }
}