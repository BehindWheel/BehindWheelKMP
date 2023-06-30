package com.egoriku.grodnoroads.setting.map.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.SettingsHeader
import com.egoriku.grodnoroads.foundation.list.MoreActionSettings
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapSettings.LocationInfo
import com.egoriku.grodnoroads.shared.appsettings.types.map.location.City.Companion.toResource

@Composable
internal fun DefaultLocationSection(
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
            icon = Icons.Outlined.HomeWork,
            text = stringResource(id = R.string.map_default_location),
            value = stringResource(id = defaultCity.current.toResource())
        ) {
            onCheckedChange(defaultCity)
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewDefaultLocationSection() = GrodnoRoadsM3ThemePreview {
    DefaultLocationSection(locationInfo = LocationInfo()) {}
}