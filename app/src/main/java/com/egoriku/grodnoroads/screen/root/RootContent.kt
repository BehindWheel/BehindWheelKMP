package com.egoriku.grodnoroads.screen.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.*
import com.egoriku.grodnoroads.screen.main.MainUi
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.root.ui.HeadLampDialog
import com.egoriku.grodnoroads.settings.alerts.AlertsScreen
import com.egoriku.grodnoroads.settings.appearance.screen.AppearanceScreen
import com.egoriku.grodnoroads.settings.faq.screen.FaqScreen
import com.egoriku.grodnoroads.settings.map.MapSettingsScreen
import com.egoriku.grodnoroads.settings.whatsnew.screen.WhatsNewScreen

@Composable
fun RootContent(roadsRootComponent: RoadsRootComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val dialogState by roadsRootComponent.headlampDialogState.collectAsState(initial = HeadLampType.None)

        if (dialogState != HeadLampType.None) {
            HeadLampDialog(
                headlampType = dialogState,
                onClose = roadsRootComponent::closeHeadlampDialog
            )
        }

        Children(
            stack = roadsRootComponent.childStack,
            animation = stackAnimation { _, _, direction ->
                if (direction.isFront) {
                    slide() + fade()
                } else {
                    scale(frontFactor = 1F, backFactor = 0.7F) + fade()
                }
            }
        ) {
            when (val child = it.instance) {
                is Child.Main -> MainUi(component = child.component)
                is Child.Appearance -> AppearanceScreen(
                    appearanceComponent = child.appearanceComponent,
                    onBack = roadsRootComponent::onBack
                )

                is Child.Map -> MapSettingsScreen(
                    mapSettingsComponent = child.mapSettingsComponent,
                    onBack = roadsRootComponent::onBack
                )

                is Child.Alerts -> AlertsScreen(
                    alertsComponent = child.alertsComponent,
                    onBack = roadsRootComponent::onBack
                )

                is Child.WhatsNew -> WhatsNewScreen(
                    whatsNewComponent = child.whatsNewComponent,
                    onBack = roadsRootComponent::onBack,
                )

                is Child.NextFeatures -> TODO()
                is Child.BetaFeatures -> TODO()
                is Child.FAQ -> FaqScreen(
                    faqComponent = child.faqComponent,
                    onBack = roadsRootComponent::onBack
                )
            }
        }
    }
}