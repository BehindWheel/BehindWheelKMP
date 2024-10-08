package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_google_map_style_detailed
import com.egoriku.grodnoroads.compose.resources.map_google_map_style_minimal
import com.egoriku.grodnoroads.compose.resources.map_header_appearance
import com.egoriku.grodnoroads.compose.resources.map_traffic_conditions_appearance
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.listitem.SwitchListItem
import com.egoriku.grodnoroads.settings.map.domain.MapStyleUrl.DARK_DETAILED
import com.egoriku.grodnoroads.settings.map.domain.MapStyleUrl.DARK_MINIMAL
import com.egoriku.grodnoroads.settings.map.domain.MapStyleUrl.LIGHT_DETAILED
import com.egoriku.grodnoroads.settings.map.domain.MapStyleUrl.LIGHT_MINIMAL
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.MapStyle
import com.egoriku.grodnoroads.settings.map.screen.ui.foundation.CheckableCard
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.Style
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MapStyleSection(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsSectionHeader(title = stringResource(Res.string.map_header_appearance))

        GoogleMapStyle(mapStyle, onCheckedChange)
        VerticalSpacer(16.dp)
        TrafficConditions(mapStyle, onCheckedChange)
    }
}

@Composable
private fun GoogleMapStyle(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    val googleMapStyle = mapStyle.googleMapStyle

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        CheckableCard(
            title = stringResource(Res.string.map_google_map_style_minimal),
            selected = googleMapStyle.style == Style.Minimal,
            imageUrl = when {
                MaterialTheme.colorScheme.isLight -> LIGHT_MINIMAL
                else -> DARK_MINIMAL
            },
            onClick = {
                onCheckedChange(googleMapStyle.copy(style = Style.Minimal))
            }
        )

        CheckableCard(
            title = stringResource(Res.string.map_google_map_style_detailed),
            selected = googleMapStyle.style == Style.Detailed,
            imageUrl = when {
                MaterialTheme.colorScheme.isLight -> LIGHT_DETAILED
                else -> DARK_DETAILED
            },
            onClick = {
                onCheckedChange(googleMapStyle.copy(style = Style.Detailed))
            }
        )
    }
}

@Composable
private fun TrafficConditions(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    val trafficJamOnMap = mapStyle.trafficJamOnMap

    SwitchListItem(
        text = stringResource(Res.string.map_traffic_conditions_appearance),
        isChecked = trafficJamOnMap.isShow,
        onCheckedChange = {
            onCheckedChange(trafficJamOnMap.copy(isShow = it))
        }
    )
}

@PreviewGrodnoRoads
@Composable
private fun PreviewMapStyleSectionPreview() = GrodnoRoadsM3ThemePreview {
    MapStyleSection(mapStyle = MapStyle()) { }
}
