package com.egoriku.grodnoroads.settings.alerts.domain.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

internal class AlertsComponentImpl(
    componentContext: ComponentContext
) : AlertsComponent, ComponentContext by componentContext, KoinComponent {

    override val state: Flow<AlertsComponent.AlertSettingsState>
        get() = TODO("Not yet implemented")
}