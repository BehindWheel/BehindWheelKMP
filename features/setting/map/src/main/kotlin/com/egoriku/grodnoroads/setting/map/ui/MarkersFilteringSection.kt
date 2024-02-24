package com.egoriku.grodnoroads.setting.map.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsHeader
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent.MapPref.MarkerFiltering
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.Filtering.Companion.toResource

@Composable
internal fun MarkersFilteringSection(
    markersFiltering: MarkerFiltering,
    modify: (MapPref) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsHeader(title = stringResource(id = R.string.map_markers_filtering))
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = stringResource(id = R.string.map_markers_filtering_description),
            color = LocalContentColor.current.copy(alpha = 0.64f),
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            markersFiltering.values.forEach { filtering ->
                val selected = markersFiltering.current == filtering

                FilterChip(
                    selected = selected,
                    onClick = {
                        modify(markersFiltering.copy(current = filtering))
                    },
                    label = {
                        Text(text = stringResource(id = filtering.toResource()))
                    }
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewMarkersFilteringSection() = GrodnoRoadsM3ThemePreview {
    MarkersFilteringSection(markersFiltering = MarkerFiltering(), modify = {})
}