package com.egoriku.grodnoroads.root.domain

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.onboarding.domain.OnboardingComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Onboarding(val component: OnboardingComponent) : Child()
        data object MainFlow : Child()
    }
}