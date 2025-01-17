package com.egoriku.grodnoroads.specialevent.domain.component.specialevent

import com.arkivanov.decompose.router.slot.ChildSlot
import com.egoriku.grodnoroads.specialevent.domain.component.dialog.DialogComponent
import kotlinx.coroutines.flow.StateFlow

interface SpecialEventComponent {

    val specialEvents: StateFlow<ChildSlot<*, DialogComponent>>
}
