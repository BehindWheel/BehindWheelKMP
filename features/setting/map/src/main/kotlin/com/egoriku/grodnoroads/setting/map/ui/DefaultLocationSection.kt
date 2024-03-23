package com.egoriku.grodnoroads.setting.map.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.listitem.MoreActionListItem
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapSettings.LocationInfo
import com.egoriku.grodnoroads.shared.appsettings.types.map.location.City.Companion.toResource

@Composable
internal fun DefaultLocationSection(
    locationInfo: LocationInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    Column {
        SettingsSectionHeader(title = stringResource(id = R.string.map_header_default_location))

        val defaultCity = locationInfo.defaultCity
        MoreActionListItem(
            iconRes = R.drawable.ic_my_city,
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