package com.egoriku.grodnoroads.onboarding.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.State
import com.egoriku.grodnoroads.shared.persistent.map.location.City
import kotlinx.coroutines.flow.Flow

@Stable
interface OnboardingComponent {
    val state: Flow<State>

    fun completeOnboarding()

    fun modify(preference: OnboardingPref)

    @Stable
    sealed interface OnboardingPref {
        data class DefaultCity(
            val current: City = City.Grodno,
            val values: List<City> = City.entries
        ) : OnboardingPref
    }
}
