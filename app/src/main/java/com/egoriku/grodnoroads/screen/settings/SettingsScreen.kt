package com.egoriku.grodnoroads.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.egoriku.grodnoroads.screen.settings.appearance.ui.AppearanceScreen
import com.egoriku.grodnoroads.screen.settings.faq.FaqScreen
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import com.egoriku.grodnoroads.screen.settings.ui.MapEventsSettings
import com.egoriku.grodnoroads.screen.settings.ui.MapPreferencesSettings
import com.egoriku.grodnoroads.screen.settings.whatsnew.WhatsNewScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun SettingsScreen(settingsComponent: SettingsComponent) {
    val settingsState by settingsComponent.settingsState.collectAsState(initial = SettingsState())

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
                    is SettingsComponent.Child.Map -> TODO()
                    is SettingsComponent.Child.Alerts -> TODO()
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

            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .verticalScroll(state = rememberScrollState())
            ) {

            }
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = rememberScrollState())
            ) {
                MapEventsSettings(
                    mapInfo = settingsState.mapInfo,
                    onCheckedChange = settingsComponent::onCheckedChanged
                )
                MapPreferencesSettings(
                    mapAppearance = settingsState.mapAppearance,
                    onCheckedChange = settingsComponent::onCheckedChanged
                )
            }
        }
    }
}
