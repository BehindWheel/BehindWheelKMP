package com.egoriku.grodnoroads.screen.main

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.chat.ChatComponent
import com.egoriku.grodnoroads.screen.map.MapComponent

interface MainComponent {

    val activeChildIndex: Value<Int>

    val routerState: Value<RouterState<*, Child>>

    sealed class Child(val index: Int) {
        data class Map(val component: MapComponent) : Child(index = 0)
        data class Chat(val component: ChatComponent) : Child(index = 1)
    }

    fun onSelectTab(index: Int)
}