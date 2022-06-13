package com.egoriku.grodnoroads.screen.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.settings.SettingsCheckbox
import com.egoriku.grodnoroads.screen.settings.SettingsComponent
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory
import com.egoriku.grodnoroads.screen.settings.ui.common.BasicSettingsSection
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun MapPreferencesSettings(
    settingsState: SettingsStoreFactory.SettingsState,
    onCheckedChange: (SettingsComponent.Pref) -> Unit
) {
    BasicSettingsSection(title = stringResource(R.string.settings_section_map_preferences)) {
        Column {
            TrafficJam(settingsState, onCheckedChange)
        }
    }
}

@Composable
private fun TrafficJam(
    settingsState: SettingsStoreFactory.SettingsState,
    onCheckedChange: (SettingsComponent.Pref) -> Unit
) {
    val trafficJam = settingsState.trafficJam

    SettingsCheckbox(
        titleId = R.string.settings_traffic_jam,
        iconId = R.drawable.ic_traffic_light,
        isChecked = trafficJam.isShow,
        onCheckedChange = {
            onCheckedChange(trafficJam.copy(isShow = it))
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Composable
fun PreviewMapPreferencesSettings() {
    GrodnoRoadsTheme {
        MapPreferencesSettings(settingsState = SettingsStoreFactory.SettingsState()) { }
    }
}
