package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_header_drive_mode_dynamic
import com.egoriku.grodnoroads.compose.resources.map_header_drive_mode_scale
import com.egoriku.grodnoroads.compose.resources.map_header_drive_mode_static
import com.egoriku.grodnoroads.compose.resources.map_header_drive_mode_zoom_in_city
import com.egoriku.grodnoroads.compose.resources.map_header_drive_mode_zoom_outside_city
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.InsideCity
import com.egoriku.grodnoroads.foundation.icons.outlined.OutsideCity
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.ClickableFloatRange
import com.egoriku.grodnoroads.foundation.uikit.listitem.BasicListItem
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.DriveModeZoom
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrivingModeSection(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SettingsSectionHeader(title = stringResource(Res.string.map_header_drive_mode_scale))

        val selectedIndex = if (driveModeZoom.dynamicZoomEnabled.isEnabled) 0 else 1

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                onClick = {
                    modify(driveModeZoom.dynamicZoomEnabled.copy(isEnabled = true))
                },
                selected = selectedIndex == 0,
                label = {
                    Text(stringResource(Res.string.map_header_drive_mode_dynamic))
                }
            )
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                onClick = {
                    modify(driveModeZoom.dynamicZoomEnabled.copy(isEnabled = false))
                },
                selected = selectedIndex == 1,
                label = {
                    Text(stringResource(Res.string.map_header_drive_mode_static))
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!driveModeZoom.dynamicZoomEnabled.isEnabled) {
                MapZoomInCity(driveModeZoom = driveModeZoom, modify = modify, reset = reset)
                MapZoomOutCity(driveModeZoom = driveModeZoom, modify = modify, reset = reset)
            }
        }
    }
}

@Composable
private fun MapZoomInCity(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    val mapZoomInCity = driveModeZoom.mapZoomInCity

    BasicListItem(
        imageVector = GrodnoRoads.Outlined.InsideCity,
        text = stringResource(Res.string.map_header_drive_mode_zoom_in_city),
        textStyle = MaterialTheme.typography.bodyMedium
    ) {
        ClickableFloatRange(
            value = mapZoomInCity.current,
            min = mapZoomInCity.min,
            max = mapZoomInCity.max,
            step = mapZoomInCity.stepSize,
            onLongClick = { reset(driveModeZoom.mapZoomInCity) },
            onValueChange = {
                modify(mapZoomInCity.copy(current = it))
            }
        )
    }
}

@Composable
private fun MapZoomOutCity(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    val mapZoomOutCity = driveModeZoom.mapZoomOutCity

    BasicListItem(
        imageVector = GrodnoRoads.Outlined.OutsideCity,
        text = stringResource(Res.string.map_header_drive_mode_zoom_outside_city),
        textStyle = MaterialTheme.typography.bodyMedium
    ) {
        ClickableFloatRange(
            value = mapZoomOutCity.current,
            min = mapZoomOutCity.min,
            max = mapZoomOutCity.max,
            step = mapZoomOutCity.stepSize,
            onLongClick = { reset(mapZoomOutCity) },
            onValueChange = {
                modify(mapZoomOutCity.copy(current = it))
            }
        )
    }
}

@PreviewGrodnoRoads
@Composable
private fun DrivingModeSectionSectionPreview() = GrodnoRoadsM3ThemePreview {
    var driveModeZoom by rememberMutableState { DriveModeZoom() }

    Box(modifier = Modifier.height(200.dp)) {
        DrivingModeSection(
            driveModeZoom = driveModeZoom,
            modify = {
                driveModeZoom = when (it) {
                    is MapPref.MapZoomInCity -> driveModeZoom.copy(mapZoomInCity = it)
                    is MapPref.MapZoomOutCity -> driveModeZoom.copy(mapZoomOutCity = it)
                    is MapPref.DynamicZoomEnabled -> driveModeZoom.copy(dynamicZoomEnabled = it)
                    else -> error("Unhandled MapPref type")
                }
            },
            reset = { driveModeZoom = DriveModeZoom() }
        )
    }
}
