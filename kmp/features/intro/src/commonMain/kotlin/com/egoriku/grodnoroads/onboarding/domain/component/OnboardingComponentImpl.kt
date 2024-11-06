package com.egoriku.grodnoroads.onboarding.domain.component

import com.arkivanov.decompose.ComponentContext

fun buildOnboardingComponent(
    componentContext: ComponentContext,
    onCompleteOnboarding: () -> Unit
): OnboardingComponent = OnboardingComponentImpl(
    componentContext = componentContext,
    onCompleteOnboarding = onCompleteOnboarding
)

internal class OnboardingComponentImpl(
    componentContext: ComponentContext,
    private val onCompleteOnboarding: () -> Unit
) : OnboardingComponent,
    ComponentContext by componentContext {

    override fun completeOnboarding() = onCompleteOnboarding()
}
