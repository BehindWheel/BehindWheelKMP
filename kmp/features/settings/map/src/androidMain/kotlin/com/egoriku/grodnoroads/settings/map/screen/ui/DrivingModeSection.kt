package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_inside_city
import com.egoriku.grodnoroads.compose.resources.ic_outside_city
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.ClickableFloatRange
import com.egoriku.grodnoroads.foundation.uikit.listitem.BasicListItem
import com.egoriku.grodnoroads.localization.R
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.DriveModeZoom

@Composable
internal fun DrivingModeSection(
    driveModeZoom: DriveModeZoom,
    modify: (MapPref) -> Unit,
    reset: (MapPref) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SettingsSectionHeader(title = stringResource(R.string.map_header_drive_mode))

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

    BasicListItem(
        drawableResource = Res.drawable.ic_inside_city,
        text = stringResource(R.string.map_header_drive_mode_zoom_in_city),
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
            },
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
        drawableResource = Res.drawable.ic_outside_city,
        text = stringResource(R.string.map_header_drive_mode_zoom_outside_city),
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
            },
        )
    }
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