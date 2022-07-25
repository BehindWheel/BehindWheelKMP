package com.egoriku.grodnoroads.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.egoriku.grodnoroads.screen.settings.alerts.AlertsScreen
import com.egoriku.grodnoroads.screen.settings.appearance.AppearanceScreen
import com.egoriku.grodnoroads.screen.settings.faq.FaqScreen
import com.egoriku.grodnoroads.screen.settings.map.MapSettingsScreen
import com.egoriku.grodnoroads.screen.settings.whatsnew.WhatsNewScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun SettingsScreen(settingsComponent: SettingsComponent) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Column {
            val childStack by settingsComponent.childStack.subscribeAsState()

            Children(
                modifier = Modifier.statusBarsPadding(),
                stack = childStack,
                animation = stackAnimation(slide())
            ) { created ->
                when (val child = created.instance) {
                    is SettingsComponent.Child.Settings -> SettingsUi { page ->
                        settingsComponent.open(page)
                    }
                    is SettingsComponent.Child.Appearance -> AppearanceScreen(
                        appearanceComponent = child.appearanceComponent,
                        onBack = settingsComponent::onBack
                    )
                    is SettingsComponent.Child.Map -> MapSettingsScreen(
                        mapSettingsComponent = child.mapSettingsComponent,
                        onBack = settingsComponent::onBack
                    )
                    is SettingsComponent.Child.Alerts -> AlertsScreen(
                        alertsComponent = child.alertsComponent,
                        onBack = settingsComponent::onBack
                    )
                    is SettingsComponent.Child.WhatsNew -> WhatsNewScreen(
                        whatsNewComponent = child.whatsNewComponent,
                        onBack = settingsComponent::onBack,
                    )
                    is SettingsComponent.Child.NextFeatures -> TODO()
                    is SettingsComponent.Child.BetaFeatures -> TODO()
                    is SettingsComponent.Child.FAQ -> FaqScreen(
                        faqComponent = child.faqComponent,
                        onBack = settingsComponent::onBack
                    )
                }
            }
        }
    }
}
