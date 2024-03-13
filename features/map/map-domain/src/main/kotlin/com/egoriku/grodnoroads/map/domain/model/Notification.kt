package com.egoriku.grodnoroads.map.domain.model

sealed interface Notification {
    data object RepostingSuccess: Notification
}