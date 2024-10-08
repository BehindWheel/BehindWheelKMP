package com.egoriku.grodnoroads.settings.map.screen.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_markers_filtering
import com.egoriku.grodnoroads.compose.resources.map_markers_filtering_description
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MarkerFiltering
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MarkersFilteringSection(
    markersFiltering: MarkerFiltering,
    modify: (MapPref) -> Unit
) {
    Column {
        SettingsSectionHeader(
            title = stringResource(Res.string.map_markers_filtering),
            description = stringResource(Res.string.map_markers_filtering_description)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            markersFiltering.values.forEach { filtering ->
                val selected = markersFiltering.current == filtering

                FilterChip(
                    selected = selected,
                    onClick = {
                        modify(markersFiltering.copy(current = filtering))
                    },
                    label = {
                        Text(
                            text = stringResource(filtering.toStringResource()),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun PreviewMarkersFilteringSectionPreview() = GrodnoRoadsM3ThemePreview {
    MarkersFilteringSection(markersFiltering = MarkerFiltering(), modify = {})
}
