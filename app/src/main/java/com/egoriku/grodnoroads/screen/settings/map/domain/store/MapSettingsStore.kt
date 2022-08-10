package com.egoriku.grodnoroads.screen.settings.map.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.*
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.Intent
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.State

interface MapSettingsStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class Modify(val preference: MapPref) : Intent
        data class OpenDialog(val preference: MapPref): Intent
        object CloseDialog: Intent
    }

    data class State(
        val mapSettingsState: MapSettingsState = MapSettingsState(),
        val mapDialogState: MapDialogState = None
    )

    sealed interface Message {
        data class NewSettings(val mapSettingsState: MapSettingsState) : Message
        data class NewDialogState(val mapDialogState: MapDialogState): Message
    }
}