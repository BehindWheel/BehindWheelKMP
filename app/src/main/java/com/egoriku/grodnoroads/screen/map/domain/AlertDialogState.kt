package com.egoriku.grodnoroads.screen.map.domain

import com.egoriku.grodnoroads.screen.map.domain.MapEvent.Reports

sealed interface AlertDialogState {

    object Closed : AlertDialogState
    data class Show(val reports: Reports) : AlertDialogState
}