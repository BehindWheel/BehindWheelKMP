package com.egoriku.grodnoroads.screen.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.egoriku.grodnoroads.extensions.common.StateData
import com.egoriku.grodnoroads.mainflow.TabsComponent
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import kotlinx.coroutines.flow.Flow

interface RoadsRootComponent : BackHandlerOwner {

    val childStack: Value<ChildStack<*, Child>>

    val themeState: Flow<StateData<Theme>>

    sealed class Child {
        data class Main(val component: TabsComponent) : Child()
    }
}