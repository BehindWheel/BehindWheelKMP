package com.egoriku.grodnoroads.specialevent.domain.component.specialevent

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.specialevent.domain.component.dialog.DialogComponent

interface SpecialEventComponent {

    val specialEvents: Value<ChildSlot<*, DialogComponent>>
}