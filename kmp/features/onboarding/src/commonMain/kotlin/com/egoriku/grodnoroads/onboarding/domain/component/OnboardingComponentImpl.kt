package com.egoriku.grodnoroads.onboarding.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent.OnboardingPref
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.Intent
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.Label
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.State
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildOnboardingComponent(
    componentContext: ComponentContext,
    onFinishOnboarding: () -> Unit
): OnboardingComponent = OnboardingComponentImpl(
    componentContext = componentContext,
    onFinishOnboarding = onFinishOnboarding
)

internal class OnboardingComponentImpl(
    componentContext: ComponentContext,
    private val onFinishOnboarding: () -> Unit
) : OnboardingComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val onboardingStore: OnboardingStore = instanceKeeper.getStore(::get)

    override val state: Flow<State> = onboardingStore.states

    init {
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            onboardingStore.labels bindTo ::bindLocationLabel
        }
    }

    override fun completeOnboarding() {
        onboardingStore.accept(Intent.CompleteOnboarding)
    }

    override fun modify(preference: OnboardingPref) {
        onboardingStore.accept(Intent.Modify(preference))
    }

    private fun bindLocationLabel(label: Label) = when (label) {
        is Label.FinishOnboarding -> onFinishOnboarding()
    }
}
