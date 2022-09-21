package com.egoriku.grodnoroads.screen.settings.map.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettingState
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.Intent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class MapSettingsComponentImpl(
    componentContext: ComponentContext
) : MapSettingsComponent, ComponentContext by componentContext, KoinComponent {

    private val mapSettingsStore = instanceKeeper.getStore { get<MapSettingsStore>() }

    override val mapSettingsState: Flow<MapSettingState>
        get() = mapSettingsStore.states.map { storeState ->
            MapSettingState(
                mapSettings = storeState.mapSettings,
                mapDialogState = storeState.mapDialogState,
            )
        }

    override val isLoading: Flow<Boolean>
        get() = mapSettingsStore.states.map { it.isLoading }

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