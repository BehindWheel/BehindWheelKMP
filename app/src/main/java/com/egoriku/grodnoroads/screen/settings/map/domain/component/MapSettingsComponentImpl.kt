package com.egoriku.grodnoroads.screen.settings.map.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.Intent
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.State
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MapSettingsComponentImpl(
    componentContext: ComponentContext
) : MapSettingsComponent, ComponentContext by componentContext, KoinComponent {

    private val mapSettingsStore = instanceKeeper.getStore { get<MapSettingsStore>() }

    override val state: Flow<State>
        get() = mapSettingsStore.states

    override fun modify(preference: MapPref) {
        mapSettingsStore.accept(Intent.Modify(preference))
    }

    override fun openDialog(preference: MapPref) {
        mapSettingsStore.accept(Intent.OpenDialog(preference))
    }

    override fun closeDialog() {
        mapSettingsStore.accept(Intent.CloseDialog)
    }
}