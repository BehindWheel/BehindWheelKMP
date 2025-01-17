package com.egoriku.grodnoroads.guidance.domain.store.dialog

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.guidance.domain.model.MapBottomSheet
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.Intent
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.Message
import com.egoriku.grodnoroads.guidance.domain.store.dialog.DialogStore.State

interface DialogStore : Store<Intent, State, Message> {

    sealed interface Intent {
        data class OpenMarkerInfoDialog(val reports: MapEvent.Reports) : Intent
        data object OpenQuickSettings : Intent
        data object CloseDialog : Intent
    }

    sealed interface Message {
        data class OpenDialog(val dialog: MapBottomSheet) : Message
        data class CloseDialog(val dialog: MapBottomSheet.None) : Message
    }

    data class State(
        val mapBottomSheet: MapBottomSheet = MapBottomSheet.None
    )
}
