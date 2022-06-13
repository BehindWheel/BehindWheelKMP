package com.egoriku.grodnoroads.screen.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.settings.SettingsCheckbox
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import com.egoriku.grodnoroads.screen.settings.ui.common.BasicSettingsSection
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun MapEventsSettings(
    settingsState: SettingsState,
    onCheckedChange: (Pref) -> Unit
) {
    BasicSettingsSection(title = stringResource(R.string.settings_section_map_information)) {
        Column {
            StationaryCameras(settingsState, onCheckedChange)
            MobileCameras(settingsState, onCheckedChange)
            TrafficPolice(settingsState, onCheckedChange)
            Incidents(settingsState, onCheckedChange)
        }
    }
}

@Composable
private fun StationaryCameras(
    settingsState: SettingsState,
    onCheckedChange: (Pref) -> Unit
) {
    val stationaryCameras = settingsState.stationaryCameras

    SettingsCheckbox(
        titleId = R.string.settings_stationary_cameras,
        iconId = R.drawable.ic_stationary_camera,
        isChecked = stationaryCameras.isShow,
        onCheckedChange = {
            onCheckedChange(stationaryCameras.copy(isShow = it))
        }
    )
}

@Composable
private fun MobileCameras(
    settingsState: SettingsState,
    onCheckedChange: (Pref) -> Unit
) {
    val mobileCameras = settingsState.mobileCameras

    SettingsCheckbox(
        titleId = R.string.settings_mobile_cameras,
        iconId = R.drawable.ic_mobile_camera,
        isChecked = mobileCameras.isShow,
        onCheckedChange = {
            onCheckedChange(mobileCameras.copy(isShow = it))
        }
    )
}

@Composable
fun TrafficPolice(
    settingsState: SettingsState,
    onCheckedChange: (Pref) -> Unit
) {
    val trafficPolice = settingsState.trafficPolice

    SettingsCheckbox(
        titleId = R.string.settings_traffic_police,
        iconId = R.drawable.ic_traffic_police,
        isChecked = trafficPolice.isShow,
        onCheckedChange = {
            onCheckedChange(trafficPolice.copy(isShow = it))
        }
    )
}

@Composable
fun Incidents(
    settingsState: SettingsState,
    onCheckedChange: (Pref) -> Unit
) {
    val incidents = settingsState.incidents

    SettingsCheckbox(
        titleId = R.string.settings_incidents,
        iconId = R.drawable.ic_warning,
        isChecked = incidents.isShow,
        onCheckedChange = {
            onCheckedChange(incidents.copy(isShow = it))
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Composable
fun PreviewMapEventsSettings() {
    GrodnoRoadsTheme {
        MapEventsSettings(settingsState = SettingsState()) { }
    }
}