package com.egoriku.grodnoroads.screen.settings.map.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettingsState
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.Intent
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.State

interface MapSettingsStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class OnCheckedChanged(val preference: MapPref) : Intent
    }

    data class State(val mapSettingsState: MapSettingsState = MapSettingsState())

    sealed interface Message {
        data class NewSettings(val mapSettingsState: MapSettingsState) : Message
    }
}