package com.egoriku.grodnoroads.screen.settings.appearance.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.*
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore.Intent

interface AppearanceStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class Modify(val preference: AppearancePref) : Intent
        data class Update(val preference: AppearancePref) : Intent
        object CloseDialog : Intent
    }

    sealed interface Message {
        data class NewSettings(val appearanceState: AppearanceState) : Message
        data class Dialog(val dialogState: AppearanceDialogState) : Message
    }
}