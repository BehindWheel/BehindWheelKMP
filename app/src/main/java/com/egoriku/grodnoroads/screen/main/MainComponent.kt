package com.egoriku.grodnoroads.screen.main

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.map.MapComponent
import com.egoriku.grodnoroads.screen.settings.SettingsComponent

interface MainComponent {

    val activeChildIndex: Value<Int>

    val routerState: Value<RouterState<*, Child>>

    sealed class Child(val index: Int) {
        data class Map(val component: MapComponent) : Child(index = 0)
        data class Settings(val component: SettingsComponent) : Child(index = 1)
    }

    fun onSelectTab(index: Int)
}