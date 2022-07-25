package com.egoriku.grodnoroads.screen.settings.map.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.list.CheckboxSettings
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettingsState.MapInfo
import com.egoriku.grodnoroads.screen.settings.ui.SettingsHeader
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun MapEventsSection(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(title = stringResource(id = R.string.map_header_markers))

        StationaryCameras(mapInfo, onCheckedChange)
        MobileCameras(mapInfo, onCheckedChange)
        TrafficPolice(mapInfo, onCheckedChange)
        RoadIncidents(mapInfo, onCheckedChange)
        CarCrash(mapInfo, onCheckedChange)
        TrafficJam(mapInfo, onCheckedChange)
        WildAnimals(mapInfo, onCheckedChange)
    }
}

@Composable
private fun StationaryCameras(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val stationaryCameras = mapInfo.stationaryCameras

    CheckboxSettings(
        iconRes = R.drawable.ic_stationary_camera,
        textRes = R.string.map_markers_stationary_cameras,
        isChecked = stationaryCameras.isShow,
        onCheckedChange = {
            onCheckedChange(stationaryCameras.copy(isShow = it))
        }
    )
}

@Composable
private fun MobileCameras(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val mobileCameras = mapInfo.mobileCameras

    CheckboxSettings(
        iconRes = R.drawable.ic_mobile_camera,
        textRes = R.string.map_markers_mobile_cameras,
        isChecked = mobileCameras.isShow,
        onCheckedChange = {
            onCheckedChange(mobileCameras.copy(isShow = it))
        }
    )
}

@Composable
private fun TrafficPolice(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val trafficPolice = mapInfo.trafficPolice

    CheckboxSettings(
        iconRes = R.drawable.ic_traffic_police,
        textRes = R.string.map_markers_traffic_police,
        isChecked = trafficPolice.isShow,
        onCheckedChange = {
            onCheckedChange(trafficPolice.copy(isShow = it))
        }
    )
}

@Composable
private fun RoadIncidents(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val roadIncident = mapInfo.roadIncident

    CheckboxSettings(
        iconRes = R.drawable.ic_warning,
        textRes = R.string.map_markers_incidents,
        isChecked = roadIncident.isShow,
        onCheckedChange = {
            onCheckedChange(roadIncident.copy(isShow = it))
        }
    )
}

@Composable
private fun CarCrash(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val carCrash = mapInfo.carCrash

    CheckboxSettings(
        iconRes = R.drawable.ic_car_crash,
        textRes = R.string.map_markers_car_crash,
        isChecked = carCrash.isShow,
        onCheckedChange = {
            onCheckedChange(carCrash.copy(isShow = it))
        }
    )
}

@Composable
private fun TrafficJam(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val trafficJam = mapInfo.trafficJam

    CheckboxSettings(
        iconRes = R.drawable.ic_traffic_jam,
        textRes = R.string.map_markers_traffic_jam,
        isChecked = trafficJam.isShow,
        onCheckedChange = {
            onCheckedChange(trafficJam.copy(isShow = it))
        }
    )
}

@Composable
private fun WildAnimals(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val wildAnimals = mapInfo.wildAnimals

    CheckboxSettings(
        iconRes = R.drawable.ic_wild_animals,
        textRes = R.string.map_markers_wild_animals,
        isChecked = wildAnimals.isShow,
        onCheckedChange = {
            onCheckedChange(wildAnimals.copy(isShow = it))
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Composable
private fun PreviewMapEventsSection() {
    GrodnoRoadsTheme {
        MapEventsSection(mapInfo = MapInfo()) { }
    }
}