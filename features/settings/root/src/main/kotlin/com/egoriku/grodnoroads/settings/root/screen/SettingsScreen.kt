package com.egoriku.grodnoroads.settings.root.screen

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.settings.root.domain.component.SettingsComponent
import com.egoriku.grodnoroads.settings.root.screen.ui.SettingsUi

@Composable
fun SettingsScreen(settingsComponent: SettingsComponent) {
    SettingsUi(
        appVersion = settingsComponent.appVersion,
        onSettingClick = settingsComponent::open
    )
}
