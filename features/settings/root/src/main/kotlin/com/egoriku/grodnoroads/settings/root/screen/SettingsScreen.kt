package com.egoriku.grodnoroads.settings.root.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.egoriku.grodnoroads.settings.alerts.AlertsScreen
import com.egoriku.grodnoroads.settings.appearance.screen.AppearanceScreen
import com.egoriku.grodnoroads.settings.faq.screen.FaqScreen
import com.egoriku.grodnoroads.settings.map.MapSettingsScreen
import com.egoriku.grodnoroads.settings.root.domain.component.SettingsComponent
import com.egoriku.grodnoroads.settings.root.screen.ui.SettingsUi
import com.egoriku.grodnoroads.settings.whatsnew.screen.WhatsNewScreen

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
                    is SettingsComponent.Child.Settings -> SettingsUi(
                        appVersion = child.settingsComponent.appVersion,
                        onSettingClick = settingsComponent::open
                    )
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
