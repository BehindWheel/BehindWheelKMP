package com.egoriku.grodnoroads.root.domain

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.coroutines.CStateFlow
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent
import com.egoriku.grodnoroads.onboarding.domain.OnboardingComponent
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme

interface RootComponent {
    val theme: CStateFlow<Theme?>
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Onboarding(val component: OnboardingComponent) : Child()
        data class MainFlow(val component: MainFlowComponent) : Child()
    }
}