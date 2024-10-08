package com.egoriku.grodnoroads.guidance.domain.model.event

sealed interface Notification {
    data object RepostingSuccess : Notification
}
