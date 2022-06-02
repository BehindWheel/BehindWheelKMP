package com.egoriku.grodnoroads.screen.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.settings.domain.Theme
import com.egoriku.grodnoroads.screen.settings.domain.Theme.System
import kotlinx.coroutines.flow.Flow

interface RoadsRootComponent {

    val routerState: Value<RouterState<*, Child>>

    val themeState: Flow<ThemeState>

    data class ThemeState(
        val theme: Theme = System
    )

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
    }
}