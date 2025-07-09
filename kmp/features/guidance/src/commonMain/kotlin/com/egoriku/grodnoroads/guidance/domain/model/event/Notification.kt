package com.egoriku.grodnoroads.guidance.domain.model.event

sealed interface Notification {
    data object RepostingSuccess : Notification
    data object ReportingDisabled : Notification
    data object ReportingTooOften : Notification
    data object GeneralError : Notification
}
