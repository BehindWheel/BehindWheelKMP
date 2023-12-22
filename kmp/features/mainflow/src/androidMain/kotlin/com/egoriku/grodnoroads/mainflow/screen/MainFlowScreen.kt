package com.egoriku.grodnoroads.mainflow.screen

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.FaultyDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.*
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.predictiveback.predictiveBackAnimation
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent.Child
import com.egoriku.grodnoroads.settings.alerts.screen.AlertsScreen
import com.egoriku.grodnoroads.settings.appearance.screen.AppearanceScreen
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
            is Child.Alerts -> AlertsScreen(
                alertsComponent = child.component,
                onBack = mainFlowComponent::onBack
            )
            is Child.Appearance -> AppearanceScreen(
                appearanceComponent = child.component,
                onBack = mainFlowComponent::onBack
            )
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