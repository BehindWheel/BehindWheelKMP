package com.egoriku.grodnoroads.setting.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.setting.domain.component.SettingsComponent
import com.egoriku.grodnoroads.setting.screen.ui.SettingsUi

@Composable
fun SettingsScreen(
    contentPadding: PaddingValues,
    settingsComponent: SettingsComponent
) {
    SettingsUi(
        contentPadding = contentPadding,
        appVersion = settingsComponent.appVersion,
        onSettingClick = settingsComponent::open
    )
}
