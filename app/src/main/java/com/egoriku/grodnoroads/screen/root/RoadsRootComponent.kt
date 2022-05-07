package com.egoriku.grodnoroads.screen.root

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.settings.SettingsComponent

interface RoadsRootComponent {

    val routerState: Value<RouterState<*, Child>>

    fun openSettings()

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
        data class Settings(val component: SettingsComponent) : Child()
    }
}