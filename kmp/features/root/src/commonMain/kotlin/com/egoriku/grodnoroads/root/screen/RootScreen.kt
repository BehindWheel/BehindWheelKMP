package com.egoriku.grodnoroads.root.screen

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.egoriku.grodnoroads.mainflow.screen.MainFlowScreen
import com.egoriku.grodnoroads.onboarding.ui.OnboardingScreen
import com.egoriku.grodnoroads.root.domain.RootComponent
import com.egoriku.grodnoroads.root.domain.RootComponent.Child

@Composable
fun RootContent(
    root: RootComponent,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier) {
        val stack by root.childStack.collectAsState()

        Children(stack = stack) {
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
