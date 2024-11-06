package com.egoriku.grodnoroads.intro

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.egoriku.grodnoroads.cityselector.CitySelectorScreen
import com.egoriku.grodnoroads.intro.domain.component.IntroComponent
import com.egoriku.grodnoroads.intro.domain.component.IntroComponent.Child.CitySelector
import com.egoriku.grodnoroads.intro.domain.component.IntroComponent.Child.Onboarding
import com.egoriku.grodnoroads.onboarding.OnboardingScreen

@Composable
fun IntroScreen(
    introComponent: IntroComponent,
    modifier: Modifier = Modifier
) {
    val childStack by introComponent.childStack.collectAsState()

    Children(
        modifier = modifier.fillMaxSize(),
        stack = childStack
    ) {
        when (val child = it.instance) {
            is Onboarding -> OnboardingScreen(
                onboardingComponent = child.component
            )
            is CitySelector -> CitySelectorScreen(
                citySelectorComponent = child.component
            )
        }
    }
}
