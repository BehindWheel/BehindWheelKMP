package com.egoriku.grodnoroads.mainflow.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import com.arkivanov.essenty.backhandler.BackHandler
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent.Child
import com.egoriku.grodnoroads.settings.alerts.screen.AlertsScreen
import com.egoriku.grodnoroads.settings.appearance.screen.AppearanceScreen
import com.egoriku.grodnoroads.settings.changelog.screen.ChangelogScreen
import com.egoriku.grodnoroads.settings.debugtools.domain.DebugToolsScreen
import com.egoriku.grodnoroads.settings.faq.screen.FaqScreen
import com.egoriku.grodnoroads.settings.map.screen.MapSettingsScreen

@Composable
fun MainFlowScreen(mainFlowComponent: MainFlowComponent) {
    val stack by mainFlowComponent.childStack.collectAsState()

    Children(
        stack = stack,
        animation = backAnimation(
            backHandler = mainFlowComponent.backHandler,
            onBack = mainFlowComponent::onBack
        )
    ) {
        when (val child = it.instance) {
            is Child.Tabs -> TabsScreen(tabsComponent = child.component)
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
            is Child.FAQ -> FaqScreen(
                faqComponent = child.component,
                onBack = mainFlowComponent::onBack
            )
            is Child.MapSettings -> MapSettingsScreen(
                mapSettingsComponent = child.component,
                onBack = mainFlowComponent::onBack
            )
            is Child.DebugTools -> DebugToolsScreen(
                debugToolsComponent = child.component,
                onBack = mainFlowComponent::onBack
            )
        }
    }
}

expect fun <C : Any, T : Any> backAnimation(
    backHandler: BackHandler,
    onBack: () -> Unit
): StackAnimation<C, T>
