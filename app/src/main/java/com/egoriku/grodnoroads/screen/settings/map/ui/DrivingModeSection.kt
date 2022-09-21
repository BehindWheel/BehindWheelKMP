package com.egoriku.grodnoroads.screen.settings.map.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.CenterFocusWeak
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.list.MoreActionSettings
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings.DriveModeZoom
import com.egoriku.grodnoroads.screen.settings.ui.SettingsHeader
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun DrivingModeSection(
    driveModeZoom: DriveModeZoom,
    onChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(
            title = stringResource(id = R.string.map_header_drive_mode),
            top = 16.dp
        )

        MapZoomInCity(driveModeZoom = driveModeZoom, onChange = onChange)
        MapZoomOutCity(driveModeZoom = driveModeZoom, onChange = onChange)
    }
}

@Composable
private fun MapZoomInCity(
    driveModeZoom: DriveModeZoom,
    onChange: (MapPref) -> Unit
) {
    val mapZoomInCity = driveModeZoom.mapZoomInCity

    MoreActionSettings(
        icon = Icons.Default.CenterFocusStrong,
        text = stringResource(R.string.map_header_drive_mode_zoom_in_city),
        value = mapZoomInCity.current.toString(),
        onClick = {
            onChange(mapZoomInCity)
        }
    )
}

@Composable
private fun MapZoomOutCity(
    driveModeZoom: DriveModeZoom,
    onChange: (MapPref) -> Unit
) {
    val mapZoomOutCity = driveModeZoom.mapZoomOutCity

    MoreActionSettings(
        icon = Icons.Default.CenterFocusWeak,
        text = stringResource(R.string.map_header_drive_mode_zoom_outside_city),
        value = mapZoomOutCity.current.toString(),
        onClick = {
            onChange(mapZoomOutCity)
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Preview(showBackground = true, locale = "be")
@Composable
private fun PreviewDrivingModeSectionSection() {
    GrodnoRoadsTheme {
        DrivingModeSection(driveModeZoom = DriveModeZoom()) {}
    }
}