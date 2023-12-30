package com.egoriku.grodnoroads.root.domain

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.coroutines.CStateFlow
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent
import com.egoriku.grodnoroads.onboarding.domain.OnboardingComponent
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme

@Stable
interface RootComponent {
    val theme: CStateFlow<Theme?>
    val childStack: CStateFlow<ChildStack<*, Child>>

    sealed class Child {
        data class Onboarding(val component: OnboardingComponent) : Child()
        data class MainFlow(val component: MainFlowComponent) : Child()
    }
}