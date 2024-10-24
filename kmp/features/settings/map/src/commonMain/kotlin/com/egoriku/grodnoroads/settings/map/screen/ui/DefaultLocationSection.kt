package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_default_location
import com.egoriku.grodnoroads.compose.resources.map_header_default_location
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.MyCity
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.listitem.MoreActionListItem
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.LocationInfo
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DefaultLocationSection(
    locationInfo: LocationInfo,
    onCheckedChange: (MapPref) -> Unit
) {
    Column {
        SettingsSectionHeader(title = stringResource(Res.string.map_header_default_location))

        val defaultCity = locationInfo.defaultCity
        MoreActionListItem(
            imageVector = GrodnoRoads.Outlined.MyCity,
            text = stringResource(Res.string.map_default_location),
            value = stringResource(defaultCity.current.toStringResource())
        ) {
            onCheckedChange(defaultCity)
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun PreviewDefaultLocationSectionPreview() = GrodnoRoadsM3ThemePreview {
    DefaultLocationSection(locationInfo = LocationInfo()) {}
}
