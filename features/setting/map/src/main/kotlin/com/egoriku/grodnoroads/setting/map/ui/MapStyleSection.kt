package com.egoriku.grodnoroads.setting.map.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.CheckableCard
import com.egoriku.grodnoroads.foundation.SettingsHeader
import com.egoriku.grodnoroads.foundation.list.SwitchSettings
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapSettings.MapStyle
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.Style

@Composable
internal fun MapStyleSection(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(title = stringResource(id = R.string.map_header_appearance))

        GoogleMapStyle(mapStyle, onCheckedChange)
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
            title = R.string.map_google_map_style_minimal,
            selected = googleMapStyle.style == Style.Minimal,
            iconId = R.drawable.ic_map_style_minimal,
            onClick = {
                onCheckedChange(googleMapStyle.copy(style = Style.Minimal))
            }
        )
        CheckableCard(
            title = R.string.map_google_map_style_detailed,
            selected = googleMapStyle.style == Style.Detailed,
            iconId = R.drawable.ic_map_style_detailed,
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

    SwitchSettings(
        modifier = Modifier.padding(start = 8.dp),
        stringResId = R.string.map_traffic_conditions_appearance,
        isChecked = trafficJamOnMap.isShow,
        onCheckedChange = {
            onCheckedChange(trafficJamOnMap.copy(isShow = it))
        }
    )
}

@GrodnoRoadsPreview
@Composable
fun PreviewMapStyleSection() = GrodnoRoadsM3ThemePreview {
    MapStyleSection(mapStyle = MapStyle()) { }
}
