package com.egoriku.grodnoroads.screen.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface RoadsRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    val state: Flow<Theme>
    val headlampDialogState: Flow<HeadLampType>

    fun closeHeadlampDialog()

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
    }
}