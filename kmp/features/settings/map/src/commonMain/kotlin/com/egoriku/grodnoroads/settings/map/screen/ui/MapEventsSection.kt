package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_header_markers
import com.egoriku.grodnoroads.compose.resources.map_markers_car_crash
import com.egoriku.grodnoroads.compose.resources.map_markers_incidents
import com.egoriku.grodnoroads.compose.resources.map_markers_medium_speed_cameras
import com.egoriku.grodnoroads.compose.resources.map_markers_mobile_cameras
import com.egoriku.grodnoroads.compose.resources.map_markers_select_all
import com.egoriku.grodnoroads.compose.resources.map_markers_stationary_cameras
import com.egoriku.grodnoroads.compose.resources.map_markers_traffic_jam
import com.egoriku.grodnoroads.compose.resources.map_markers_traffic_police
import com.egoriku.grodnoroads.compose.resources.map_markers_wild_animals
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.colored.CarCrash
import com.egoriku.grodnoroads.foundation.icons.colored.MediumSpeedCamera
import com.egoriku.grodnoroads.foundation.icons.colored.MobileCamera
import com.egoriku.grodnoroads.foundation.icons.colored.RoadIncident
import com.egoriku.grodnoroads.foundation.icons.colored.StationaryCamera
import com.egoriku.grodnoroads.foundation.icons.colored.TrafficJam
import com.egoriku.grodnoroads.foundation.icons.colored.TrafficPolice
import com.egoriku.grodnoroads.foundation.icons.colored.WildAnimals
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.listitem.CheckBoxListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.TriStateCheckBoxListItem
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.MapInfo
import com.egoriku.grodnoroads.shared.persistent.Selectable
import org.jetbrains.compose.resources.stringResource

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
                onCheckedChange(MapPref.SelectAll(selectAll = selectAll))
            }
        }

        SettingsSectionHeader(title = stringResource(Res.string.map_header_markers))
        TriStateCheckBoxListItem(
            text = stringResource(Res.string.map_markers_select_all),
            state = state,
            onToggle = onToggle
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            StationaryCameras(mapInfo, onCheckedChange)
            MediumSpeedCameras(mapInfo, onCheckedChange)
            MobileCameras(mapInfo, onCheckedChange)
            TrafficPolice(mapInfo, onCheckedChange)
            WildAnimals(mapInfo, onCheckedChange)
            RoadIncidents(mapInfo, onCheckedChange)
            TrafficConditions(mapInfo, onCheckedChange)
            CarCrash(mapInfo, onCheckedChange)
        }
    }
}

private val subGroupPaddingValues = PaddingValues(start = 34.dp)
private val iconSize = DpSize(32.dp, 32.dp)

@Composable
private fun StationaryCameras(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val stationaryCameras = mapInfo.stationaryCameras

    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.StationaryCamera,
        iconSize = iconSize,
        text = stringResource(Res.string.map_markers_stationary_cameras),
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

    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.MediumSpeedCamera,
        iconSize = iconSize,
        text = stringResource(Res.string.map_markers_medium_speed_cameras),
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

    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.MobileCamera,
        iconSize = iconSize,
        text = stringResource(Res.string.map_markers_mobile_cameras),
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

    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.TrafficPolice,
        iconSize = iconSize,
        text = stringResource(Res.string.map_markers_traffic_police),
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

    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.RoadIncident,
        iconSize = iconSize,
        text = stringResource(Res.string.map_markers_incidents),
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

    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.CarCrash,
        iconSize = iconSize,
        text = stringResource(Res.string.map_markers_car_crash),
        isChecked = carCrash.isShow,
        onCheckedChange = {
            onCheckedChange(carCrash.copy(isShow = it))
        }
    )
}

@Composable
private fun TrafficConditions(
    mapInfo: MapInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val trafficJam = mapInfo.trafficJam

    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.TrafficJam,
        iconSize = iconSize,
        text = stringResource(Res.string.map_markers_traffic_jam),
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

    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.WildAnimals,
        iconSize = iconSize,
        text = stringResource(Res.string.map_markers_wild_animals),
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