package com.egoriku.grodnoroads.screen.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.settings.SettingsCheckbox
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState.MapInfo
import com.egoriku.grodnoroads.screen.settings.ui.common.BasicSettingsSection
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun MapEventsSettings(
    mapInfo: MapInfo,
    onCheckedChange: (Pref) -> Unit
) {
    BasicSettingsSection(title = stringResource(R.string.settings_section_markers)) {
        Column {
            StationaryCameras(mapInfo, onCheckedChange)
            MobileCameras(mapInfo, onCheckedChange)
            TrafficPolice(mapInfo, onCheckedChange)
            RoadIncidents(mapInfo, onCheckedChange)
            CarCrash(mapInfo, onCheckedChange)
            TrafficJam(mapInfo, onCheckedChange)
            WildAnimals(mapInfo, onCheckedChange)
        }
    }
}

@Composable
private fun StationaryCameras(
    mapInfo: MapInfo,
    onCheckedChange: (Pref) -> Unit
) {
    val stationaryCameras = mapInfo.stationaryCameras

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
    mapInfo: MapInfo,
    onCheckedChange: (Pref) -> Unit
) {
    val mobileCameras = mapInfo.mobileCameras

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
private fun TrafficPolice(
    mapInfo: MapInfo,
    onCheckedChange: (Pref) -> Unit
) {
    val trafficPolice = mapInfo.trafficPolice

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
private fun RoadIncidents(
    mapInfo: MapInfo,
    onCheckedChange: (Pref) -> Unit
) {
    val roadIncident = mapInfo.roadIncident

    SettingsCheckbox(
        titleId = R.string.settings_incidents,
        iconId = R.drawable.ic_warning,
        isChecked = roadIncident.isShow,
        onCheckedChange = {
            onCheckedChange(roadIncident.copy(isShow = it))
        }
    )
}

@Composable
private fun CarCrash(
    mapInfo: MapInfo,
    onCheckedChange: (Pref) -> Unit
) {
    val carCrash = mapInfo.carCrash

    SettingsCheckbox(
        titleId = R.string.settings_car_crash,
        iconId = R.drawable.ic_car_crash,
        isChecked = carCrash.isShow,
        onCheckedChange = {
            onCheckedChange(carCrash.copy(isShow = it))
        }
    )
}

@Composable
private fun TrafficJam(
    mapInfo: MapInfo,
    onCheckedChange: (Pref) -> Unit
) {
    val trafficJam = mapInfo.trafficJam

    SettingsCheckbox(
        titleId = R.string.settings_traffic_jam,
        iconId = R.drawable.ic_traffic_jam,
        isChecked = trafficJam.isShow,
        onCheckedChange = {
            onCheckedChange(trafficJam.copy(isShow = it))
        }
    )
}

@Composable
private fun WildAnimals(
    mapInfo: MapInfo,
    onCheckedChange: (Pref) -> Unit
) {
    val wildAnimals = mapInfo.wildAnimals

    SettingsCheckbox(
        titleId = R.string.settings_wild_animals,
        iconId = R.drawable.ic_wild_animals,
        isChecked = wildAnimals.isShow,
        onCheckedChange = {
            onCheckedChange(wildAnimals.copy(isShow = it))
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Composable
fun PreviewMapEventsSettings() {
    GrodnoRoadsTheme {
        MapEventsSettings(mapInfo = MapInfo()) { }
    }
}