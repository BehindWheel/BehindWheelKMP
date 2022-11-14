package com.egoriku.grodnoroads.screen.settings.alerts.domain.component

import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.StoreState
import kotlinx.coroutines.flow.Flow

interface AlertsComponent {

    val state: Flow<StoreState>

    data class AlertSettingsState(
        val alertDistance: AlertDistance = AlertDistance()
    ) {
        data class AlertDistance(val distance: Int = -1)
    }
}