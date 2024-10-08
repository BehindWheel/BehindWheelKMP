package com.egoriku.grodnoroads.onboarding.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent

object OnboardingViewController {

    fun create(onboardingComponent: OnboardingComponent) = ComposeUIViewController {
        GrodnoRoadsM3Theme {
            OnboardingScreen(onboardingComponent)
        }
    }
}
