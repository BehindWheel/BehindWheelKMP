package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_traffic_conditions_appearance
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.listitem.SwitchListItem
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.MapStyle
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MapStyleSection(
    mapStyle: MapStyle,
    onCheckedChange: (MapPref) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        TrafficConditions(mapStyle, onCheckedChange)
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
