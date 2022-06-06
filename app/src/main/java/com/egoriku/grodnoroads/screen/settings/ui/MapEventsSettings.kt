package com.egoriku.grodnoroads.screen.settings.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        title = {
            Text(text = stringResource(R.string.settings_stationary_cameras))
        },
        icon = {
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.ic_stationary_camera),
                contentDescription = null
            )
        },
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
        title = {
            Text(text = stringResource(R.string.settings_mobile_cameras))
        },
        icon = {
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.ic_mobile_camera),
                contentDescription = null
            )
        },
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
        title = {
            Text(text = stringResource(R.string.settings_traffic_police))
        },
        icon = {
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.ic_traffic_police),
                contentDescription = null
            )
        },
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
        title = {
            Text(text = stringResource(R.string.settings_incidents))
        },
        icon = {
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = R.drawable.ic_warning),
                contentDescription = null
            )
        },
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