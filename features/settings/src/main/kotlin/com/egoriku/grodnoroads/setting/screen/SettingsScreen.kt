package com.egoriku.grodnoroads.setting.screen

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.setting.domain.component.SettingsComponent
import com.egoriku.grodnoroads.setting.screen.ui.SettingsUi

@Composable
fun SettingsScreen(settingsComponent: SettingsComponent) {
    SettingsUi(
        appVersion = settingsComponent.appVersion,
        onSettingClick = settingsComponent::open
    )
}
