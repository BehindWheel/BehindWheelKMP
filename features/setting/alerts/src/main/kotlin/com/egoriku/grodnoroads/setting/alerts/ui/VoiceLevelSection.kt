package com.egoriku.grodnoroads.setting.alerts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.SettingsHeader
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent.AlertsPref
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent.AlertsPref.AlertVolumeLevel
import com.egoriku.grodnoroads.shared.appsettings.types.alert.VolumeLevel
import com.egoriku.grodnoroads.shared.appsettings.types.alert.VolumeLevel.Companion.toResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceLevelSection(
    alertVolumeLevel: AlertVolumeLevel,
    modify: (AlertsPref) -> Unit,
    playSound: (VolumeLevel) -> Unit
) {
    Column {
        SettingsHeader(
            title = stringResource(id = R.string.alerts_header_volume_level),
            paddingValues = PaddingValues(start = 24.dp, bottom = 4.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            alertVolumeLevel.values.forEach { volumeLevel ->
                val selected = alertVolumeLevel.current == volumeLevel

                FilterChip(
                    selected = selected,
                    onClick = {
                        modify(alertVolumeLevel.copy(current = volumeLevel))
                        playSound(volumeLevel)
                    },
                    label = {
                        Text(text = stringResource(id = volumeLevel.toResource()))
                    },
                    border = FilterChipDefaults.filterChipBorder(
                        selectedBorderWidth = 1.dp,
                        selectedBorderColor = MaterialTheme.colorScheme.outline,
                        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ),
                    leadingIcon = if (selected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    }
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun VoiceLevelSectionPreview() = GrodnoRoadsM3ThemePreview {
    VoiceLevelSection(
        alertVolumeLevel = AlertVolumeLevel(),
        modify = {},
        playSound = {}
    )
}