package com.egoriku.grodnoroads.setting.map.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.CenterFocusWeak
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.ClickableRange
import com.egoriku.grodnoroads.foundation.SettingsHeader
import com.egoriku.grodnoroads.foundation.list.ActionSettings
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapSettings.DriveModeZoom

@Composable
internal fun DrivingModeSection(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(
            title = stringResource(id = R.string.map_header_drive_mode),
            top = 16.dp
        )

        MapZoomInCity(driveModeZoom = driveModeZoom, modify = modify, reset = reset)
        MapZoomOutCity(driveModeZoom = driveModeZoom, modify = modify, reset = reset)
    }
}

@Composable
private fun MapZoomInCity(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    val mapZoomInCity = driveModeZoom.mapZoomInCity

    ActionSettings(
        icon = Icons.Default.CenterFocusStrong,
        text = stringResource(R.string.map_header_drive_mode_zoom_in_city),
        trailing = {
            ClickableRange(
                value = mapZoomInCity.current,
                min = mapZoomInCity.min,
                max = mapZoomInCity.max,
                step = mapZoomInCity.stepSize,
                onLongClick = { reset(driveModeZoom.mapZoomInCity) },
                onValueChange = {
                    modify(mapZoomInCity.copy(current = it))
                },
            )
        }
    )
}

@Composable
private fun MapZoomOutCity(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    val mapZoomOutCity = driveModeZoom.mapZoomOutCity

    ActionSettings(
        icon = Icons.Default.CenterFocusWeak,
        text = stringResource(R.string.map_header_drive_mode_zoom_outside_city),
        trailing = {
            ClickableRange(
                value = mapZoomOutCity.current,
                min = mapZoomOutCity.min,
                max = mapZoomOutCity.max,
                step = mapZoomOutCity.stepSize,
                onLongClick = { reset(mapZoomOutCity) },
                onValueChange = {
                    modify(mapZoomOutCity.copy(current = it))
                },
            )
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun DrivingModeSectionSectionPreview() = GrodnoRoadsM3ThemePreview {
    DrivingModeSection(
        driveModeZoom = DriveModeZoom(),
        modify = {},
        reset = {}
    )
}