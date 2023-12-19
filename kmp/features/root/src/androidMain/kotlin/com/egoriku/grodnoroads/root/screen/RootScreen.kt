package com.egoriku.grodnoroads.root.screen

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.egoriku.grodnoroads.mainflow.screen.MainFlowScreen
import com.egoriku.grodnoroads.onboarding.screen.OnboardingScreen
import com.egoriku.grodnoroads.root.domain.RootComponent
import com.egoriku.grodnoroads.root.domain.RootComponent.Child

@Composable
fun RootContent(root: RootComponent) {
    Surface {
        Children(stack = root.childStack) {
            when (val child = it.instance) {
                is Child.MainFlow -> {
                    MainFlowScreen(mainFlowComponent = child.component)
                }
                is Child.Onboarding -> {
                    OnboardingScreen(onboardingComponent = child.component)
                }
            }
        }
    }
}