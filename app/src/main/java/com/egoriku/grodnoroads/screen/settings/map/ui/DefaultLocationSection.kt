package com.egoriku.grodnoroads.screen.settings.map.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.CenterFocusWeak
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.list.MoreActionSettings
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Companion.toResource
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings.LocationInfo
import com.egoriku.grodnoroads.screen.settings.ui.SettingsHeader
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun DefaultLocationSection(
    locationInfo: LocationInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(
            title = stringResource(id = R.string.map_header_default_location),
            top = 16.dp
        )

        val defaultCity = locationInfo.defaultCity
        MoreActionSettings(
            icon = Icons.Default.LocationCity,
            text = stringResource(id = R.string.map_default_location),
            value = stringResource(id = defaultCity.current.toResource())
        ) {
            onCheckedChange(defaultCity)
        }

        MapZoomInCity(locationInfo = locationInfo, onCheckedChange = onCheckedChange)
        MapZoomOutCity(locationInfo = locationInfo, onCheckedChange = onCheckedChange)
    }
}


@Composable
private fun MapZoomInCity(
    locationInfo: LocationInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val mapZoomInCity = locationInfo.mapZoomInCity

    MoreActionSettings(
        icon = Icons.Default.CenterFocusStrong,
        text = "Map Zoom in city",
        value = mapZoomInCity.current.toString(),
        onClick = {
            onCheckedChange(mapZoomInCity)
        }
    )
}

@Composable
private fun MapZoomOutCity(
    locationInfo: LocationInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    val mapZoomOutCity = locationInfo.mapZoomOutCity

    MoreActionSettings(
        icon = Icons.Default.CenterFocusWeak,
        text = "Map Zoom out city",
        value = mapZoomOutCity.current.toString(),
        onClick = {
            onCheckedChange(mapZoomOutCity)
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Preview(showBackground = true, locale = "be")
@Composable
private fun PreviewDefaultLocationSection() {
    GrodnoRoadsTheme {
        DefaultLocationSection(locationInfo = LocationInfo()) {}
    }
}