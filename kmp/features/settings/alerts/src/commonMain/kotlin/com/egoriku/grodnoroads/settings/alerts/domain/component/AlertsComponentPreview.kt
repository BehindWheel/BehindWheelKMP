package com.egoriku.grodnoroads.settings.alerts.domain.component

import com.egoriku.grodnoroads.extensions.CFlow
import com.egoriku.grodnoroads.extensions.asCFlow
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertState
import kotlinx.coroutines.flow.flowOf

class AlertsComponentPreview : AlertsComponent {

    override val state: CFlow<AlertState>
        get() = flowOf(AlertState(isLoading = false)).asCFlow()

    override fun modify(pref: AlertsComponent.AlertsPref) = Unit
    override fun reset(pref: AlertsComponent.AlertsPref) = Unit
}