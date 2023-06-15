package com.egoriku.grodnoroads.setting.alerts.domain.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

fun buildAlertsComponent(
    componentContext: ComponentContext
): AlertsComponent = AlertsComponentImpl(componentContext)


internal class AlertsComponentImpl(
    componentContext: ComponentContext
) : AlertsComponent, ComponentContext by componentContext, KoinComponent {

    override val state: Flow<AlertsComponent.AlertSettingsState>
        get() = TODO("Not yet implemented")
}