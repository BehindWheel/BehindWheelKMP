package com.egoriku.grodnoroads.screen.settings.alerts.domain.component

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class AlertsComponentImpl(
    componentContext: ComponentContext
) : AlertsComponent, ComponentContext by componentContext, KoinComponent {

    override val state: Flow<MapSettingsStore.StoreState>
        get() = TODO("Not yet implemented")
}