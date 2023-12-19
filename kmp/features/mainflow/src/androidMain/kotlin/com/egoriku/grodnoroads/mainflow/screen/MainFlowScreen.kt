package com.egoriku.grodnoroads.mainflow.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.FaultyDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.*
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.predictiveback.predictiveBackAnimation
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent.Child
import com.egoriku.grodnoroads.settings.changelog.screen.ChangelogScreen
import com.egoriku.grodnoroads.settings.faq.screen.FaqScreen

@OptIn(ExperimentalDecomposeApi::class, FaultyDecomposeApi::class)
@Composable
fun MainFlowScreen(mainFlowComponent: MainFlowComponent) {
    Children(
        stack = mainFlowComponent.childStack,
        animation = predictiveBackAnimation(
            backHandler = mainFlowComponent.backHandler,
            animation = stackAnimation { _, _, direction ->
                if (direction.isFront) {
                    slide() + fade()
                } else {
                    scale(frontFactor = 1F, backFactor = 0.7F) + fade()
                }
            },
            onBack = mainFlowComponent::onBack,
        )
    ) {
        when (val child = it.instance) {
            is Child.Tabs -> {
                TabsScreen(tabsComponent = child.component)
            }
            is Child.Appearance -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("appearance")
                }
            }
            is Child.Changelog -> ChangelogScreen(
                changelogComponent = child.component,
                onBack = mainFlowComponent::onBack
            )
            is Child.Faq -> FaqScreen(
                faqComponent = child.component,
                onBack = mainFlowComponent::onBack
            )
        }
    }
}