package com.egoriku.grodnoroads.onboarding.di

import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStoreFactory
import org.koin.dsl.module

val onboardingModule = module {
    factory {
        OnboardingStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}