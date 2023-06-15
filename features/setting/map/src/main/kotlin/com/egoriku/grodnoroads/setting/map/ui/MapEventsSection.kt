package com.egoriku.grodnoroads.setting.map.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.SettingsHeader
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.list.CheckboxSettings
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.TriStateCheckbox
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapSettings.MapInfo
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapSettings.MapInfo.Selectable

@Composable
internal fun MapEventsSection(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val state by rememberMutableState(mapInfo.selectable) {
            when (mapInfo.selectable) {
                Selectable.AllDisabled -> ToggleableState.Off
                Selectable.AllEnabled -> ToggleableState.On
                Selectable.Mixed -> ToggleableState.Indeterminate
            }
        }
        val onToggle = remember(mapInfo.selectable) {
            {
                val selectAll = mapInfo.selectable != Selectable.AllEnabled
                onCheckedChange(MapPref.Selectable(selectAll = selectAll))
            }
        }

        SettingsHeader(title = stringResource(id = R.string.map_header_markers))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .triStateToggleable(state = state, onClick = onToggle),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TriStateCheckbox(
                modifier = Modifier.padding(start = 8.dp),
                state = state,
                onCheckedChange = onToggle
            )
            Text(text = stringResource(id = R.string.map_markers_select_all))
        }

        StationaryCameras(mapInfo, onCheckedChange)
        MediumSpeedCameras(mapInfo, onCheckedChange)
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
        leadingPaddingValues = PaddingValues(start = 16.dp),
        iconRes = R.drawable.ic_stationary_camera,
        stringResId = R.string.map_markers_stationary_cameras,
        isChecked = stationaryCameras.isShow,
        onCheckedChange = {
            onCheckedChange(stationaryCameras.copy(isShow = it))
        }
    )
}

@Composable
private fun MediumSpeedCameras(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val mediumSpeedCameras = mapInfo.mediumSpeedCameras

    CheckboxSettings(
        leadingPaddingValues = PaddingValues(start = 16.dp),
        iconRes = R.drawable.ic_medium_speed_camera,
        stringResId = R.string.map_markers_medium_speed_cameras,
        isChecked = mediumSpeedCameras.isShow,
        onCheckedChange = {
            onCheckedChange(mediumSpeedCameras.copy(isShow = it))
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
        leadingPaddingValues = PaddingValues(start = 16.dp),
        iconRes = R.drawable.ic_mobile_camera,
        stringResId = R.string.map_markers_mobile_cameras,
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
        leadingPaddingValues = PaddingValues(start = 16.dp),
        iconRes = R.drawable.ic_traffic_police,
        stringResId = R.string.map_markers_traffic_police,
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
        leadingPaddingValues = PaddingValues(start = 16.dp),
        iconRes = R.drawable.ic_warning,
        stringResId = R.string.map_markers_incidents,
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
        leadingPaddingValues = PaddingValues(start = 16.dp),
        iconRes = R.drawable.ic_car_crash,
        stringResId = R.string.map_markers_car_crash,
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
        leadingPaddingValues = PaddingValues(start = 16.dp),
        iconRes = R.drawable.ic_traffic_jam,
        stringResId = R.string.map_markers_traffic_jam,
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
        leadingPaddingValues = PaddingValues(start = 16.dp),
        iconRes = R.drawable.ic_wild_animals,
        stringResId = R.string.map_markers_wild_animals,
        isChecked = wildAnimals.isShow,
        onCheckedChange = {
            onCheckedChange(wildAnimals.copy(isShow = it))
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewMapEventsSection() = GrodnoRoadsM3ThemePreview {
    MapEventsSection(mapInfo = MapInfo()) { }
}