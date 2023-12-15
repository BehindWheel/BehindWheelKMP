package com.egoriku.grodnoroads.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface MainFlowComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Tabs(val tabsComponent: TabsComponent) : Child()
        data object Appearance : Child()
    }
}