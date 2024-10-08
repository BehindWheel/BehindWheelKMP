package com.egoriku.grodnoroads.quicksettings.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.quicksettings.domain.model.QuickSettingsState
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore.Intent

internal interface QuickSettingsStore : Store<Intent, QuickSettingsState, Nothing> {
    sealed interface Intent {
        data class Update(val preference: QuickSettingsPref) : Intent
    }

    sealed interface Message {
        data class NewSettings(val appearanceState: QuickSettingsState) : Message
    }
}
