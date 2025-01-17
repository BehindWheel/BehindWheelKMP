package com.egoriku.grodnoroads.intro.domain.component

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent
import kotlinx.coroutines.flow.StateFlow

@Stable
interface IntroComponent {
    val childStack: StateFlow<ChildStack<*, Child>>

    sealed interface Child {
        data class Onboarding(val component: OnboardingComponent) : Child
        data class CitySelector(val component: CitySelectorComponent) : Child
    }
}
