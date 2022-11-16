package com.egoriku.grodnoroads.screen.settings.alerts.domain.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class AlertsComponentImpl(
    componentContext: ComponentContext
) : AlertsComponent, ComponentContext by componentContext, KoinComponent {

    override val state: Flow<AlertsComponent.AlertSettingsState>
        get() = TODO("Not yet implemented")
}