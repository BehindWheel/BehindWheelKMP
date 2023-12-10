package com.egoriku.grodnoroads.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data object Onboarding : Child()
        data object MainFlow : Child()
    }
}