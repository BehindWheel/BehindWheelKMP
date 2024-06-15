package com.egoriku.grodnoroads.onboarding.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent.OnboardingPref
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent.OnboardingPref.DefaultCity
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.Intent
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.Label
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.State
import com.egoriku.grodnoroads.shared.persistent.map.location.City

interface OnboardingStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object CompleteOnboarding : Intent
        data class Modify(val preference: OnboardingPref) : Intent
    }

    data class State(
        val defaultCity: DefaultCity = DefaultCity()
    )

    sealed interface Label {
        data object FinishOnboarding: Label
    }

    sealed interface Message {
        data class OnUpdateCity(val city: City) : Message
    }
}