package com.egoriku.grodnoroads.screen.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.settings.store.SettingsStore
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.*
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.Intent.OnCheckedChanged
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.Intent.ProcessPreferenceClick
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class SettingsComponentImpl(
    componentContext: ComponentContext
) : SettingsComponent, KoinComponent, ComponentContext by componentContext {

    private val settingsStore = instanceKeeper.getStore { get<SettingsStore>() }

    override val settingsState: Flow<SettingsState> = settingsStore.states.map {
        it.settingsState
    }

    override val dialogState: Flow<DialogState> = settingsStore.states.map {
        it.dialogState
    }

    override fun onCheckedChanged(preference: SettingsComponent.Pref) {
        settingsStore.accept(OnCheckedChanged(preference))
    }

    override fun process(preference: SettingsComponent.Pref) {
        settingsStore.accept(ProcessPreferenceClick(preference = preference))
    }

    override fun processResult(preference: SettingsComponent.Pref) {
        settingsStore.accept(Intent.ProcessDialogResult(preference = preference))
    }

    override fun closeDialog() {
        settingsStore.accept(Intent.CloseDialog)
    }
}