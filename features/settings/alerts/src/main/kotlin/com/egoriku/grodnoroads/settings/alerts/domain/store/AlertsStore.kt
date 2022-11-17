package com.egoriku.grodnoroads.settings.alerts.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettingsState
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStore.State

internal interface AlertsStore : Store<Nothing, State, Nothing> {

    data class State(
        val alertDistance: AlertSettingsState = AlertSettingsState()
    )

    sealed interface Message {
        data class NewSettings(val alertSettingsState: AlertSettingsState) : Message
    }
}