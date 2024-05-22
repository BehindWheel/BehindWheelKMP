package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.listitem.MoreActionListItem
import com.egoriku.grodnoroads.localization.R
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.LocationInfo
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import com.egoriku.grodnoroads.shared.resources.MR
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DefaultLocationSection(
    locationInfo: LocationInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    Column {
        SettingsSectionHeader(title = stringResource(R.string.map_header_default_location))

        val defaultCity = locationInfo.defaultCity
        MoreActionListItem(
            imageResource = MR.images.ic_my_city,
            text = stringResource(R.string.map_default_location),
            value = stringResource(defaultCity.current.toStringResource())
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