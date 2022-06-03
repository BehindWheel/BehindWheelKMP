package com.egoriku.grodnoroads.screen.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory
import kotlinx.coroutines.flow.Flow

interface RoadsRootComponent {

    val routerState: Value<RouterState<*, Child>>

    val themeState: Flow<RootStoreFactory.State>

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
    }
}