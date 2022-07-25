package com.egoriku.grodnoroads.screen.settings.alerts.domain.component

import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.State
import kotlinx.coroutines.flow.Flow

interface AlertsComponent {

    val state: Flow<State>

    data class AlertSettingsState(
        val alertDistance: AlertDistance = AlertDistance()
    ) {
        data class AlertDistance(val distance: Int = 600)
    }
}