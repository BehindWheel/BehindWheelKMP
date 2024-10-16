package com.egoriku.grodnoroads.settings.alerts.screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.alerts_header_voice_alerts
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_car_crash
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_incidents
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_medium_speed_cameras
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_mobile_cameras
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_select_all
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_stationary_cameras
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_traffic_jam
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_traffic_police
import com.egoriku.grodnoroads.compose.resources.alerts_notifications_wild_animals
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
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.listitem.CheckBoxListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.TriStateCheckBoxListItem
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertSettings.AlertEvents
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.CarCrash
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.MediumSpeedCameras
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.MobileCameras
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.RoadIncident
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.SelectAll
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.StationaryCameras
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.TrafficJam
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.TrafficPolice
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.WildAnimals
import com.egoriku.grodnoroads.shared.persistent.Selectable
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlertEventsSection(
    alertEvents: AlertEvents,
    modifier: Modifier = Modifier,
    onCheckedChange: (AlertsPref) -> Unit
) {
    Column(modifier = modifier) {
        val state by rememberMutableState(alertEvents.selectable) {
            when (alertEvents.selectable) {
                Selectable.AllDisabled -> ToggleableState.Off
                Selectable.AllEnabled -> ToggleableState.On
                Selectable.Mixed -> ToggleableState.Indeterminate
            }
        }
        val onToggle = remember(alertEvents.selectable) {
            {
                val selectAll = alertEvents.selectable != Selectable.AllEnabled
                onCheckedChange(SelectAll(selectAll = selectAll))
            }
        }

        SettingsSectionHeader(title = stringResource(Res.string.alerts_header_voice_alerts))

        TriStateCheckBoxListItem(
            text = stringResource(Res.string.alerts_notifications_select_all),
            state = state,
            onToggle = onToggle
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            StationaryCameras(alertEvents.stationaryCameras, onCheckedChange)
            MediumSpeedCameras(alertEvents.mediumSpeedCameras, onCheckedChange)
            MobileCameras(alertEvents.mobileCameras, onCheckedChange)
            TrafficPolice(alertEvents.trafficPolice, onCheckedChange)
            WildAnimals(alertEvents.wildAnimals, onCheckedChange)
            RoadIncidents(alertEvents.roadIncident, onCheckedChange)
            TrafficConditions(alertEvents.trafficJam, onCheckedChange)
            CarCrash(alertEvents.carCrash, onCheckedChange)
        }
    }
}

private val subGroupPaddingValues = PaddingValues(start = 34.dp)
private val iconSize = DpSize(32.dp, 32.dp)

@Composable
private fun StationaryCameras(
    stationaryCameras: StationaryCameras,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.StationaryCamera,
        iconSize = iconSize,
        text = stringResource(Res.string.alerts_notifications_stationary_cameras),
        isChecked = stationaryCameras.isNotify,
        onCheckedChange = {
            onCheckedChange(stationaryCameras.copy(isNotify = it))
        }
    )
}

@Composable
private fun MediumSpeedCameras(
    mediumSpeedCameras: MediumSpeedCameras,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.MediumSpeedCamera,
        iconSize = iconSize,
        text = stringResource(Res.string.alerts_notifications_medium_speed_cameras),
        isChecked = mediumSpeedCameras.isNotify,
        onCheckedChange = {
            onCheckedChange(mediumSpeedCameras.copy(isNotify = it))
        }
    )
}

@Composable
private fun MobileCameras(
    mobileCameras: MobileCameras,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.MobileCamera,
        iconSize = iconSize,
        text = stringResource(Res.string.alerts_notifications_mobile_cameras),
        isChecked = mobileCameras.isNotify,
        onCheckedChange = {
            onCheckedChange(mobileCameras.copy(isNotify = it))
        }
    )
}

@Composable
private fun TrafficPolice(
    trafficPolice: TrafficPolice,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.TrafficPolice,
        iconSize = iconSize,
        text = stringResource(Res.string.alerts_notifications_traffic_police),
        isChecked = trafficPolice.isNotify,
        onCheckedChange = {
            onCheckedChange(trafficPolice.copy(isNotify = it))
        }
    )
}

@Composable
private fun RoadIncidents(
    roadIncident: RoadIncident,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.RoadIncident,
        iconSize = iconSize,
        text = stringResource(Res.string.alerts_notifications_incidents),
        isChecked = roadIncident.isNotify,
        onCheckedChange = {
            onCheckedChange(roadIncident.copy(isNotify = it))
        }
    )
}

@Composable
private fun CarCrash(
    carCrash: CarCrash,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.CarCrash,
        iconSize = iconSize,
        text = stringResource(Res.string.alerts_notifications_car_crash),
        isChecked = carCrash.isNotify,
        onCheckedChange = {
            onCheckedChange(carCrash.copy(isNotify = it))
        }
    )
}

@Composable
private fun TrafficConditions(
    trafficJam: TrafficJam,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.TrafficJam,
        iconSize = iconSize,
        text = stringResource(Res.string.alerts_notifications_traffic_jam),
        isChecked = trafficJam.isNotify,
        onCheckedChange = {
            onCheckedChange(trafficJam.copy(isNotify = it))
        }
    )
}

@Composable
private fun WildAnimals(
    wildAnimals: WildAnimals,
    onCheckedChange: (AlertsPref) -> Unit
) {
    CheckBoxListItem(
        paddingValues = subGroupPaddingValues,
        imageVector = GrodnoRoads.Colored.WildAnimals,
        iconSize = iconSize,
        text = stringResource(Res.string.alerts_notifications_wild_animals),
        isChecked = wildAnimals.isNotify,
        onCheckedChange = {
            onCheckedChange(wildAnimals.copy(isNotify = it))
        }
    )
}

@PreviewGrodnoRoads
@Composable
private fun AlertEventsSectionPreview() = GrodnoRoadsM3ThemePreview {
    AlertEventsSection(alertEvents = AlertEvents()) {}
}
