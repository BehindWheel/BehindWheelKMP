package com.egoriku.grodnoroads.settings.alerts.screen.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.foundation.uikit.listitem.SimpleListItem
import com.egoriku.grodnoroads.resources.MR
import com.egoriku.grodnoroads.resources.stringResource
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.AlertVolumeLevel
import com.egoriku.grodnoroads.shared.persistent.alert.VolumeLevel

@Composable
fun VoiceLevelSection(
    alertVolumeLevel: AlertVolumeLevel,
    modify: (AlertsPref) -> Unit,
    playTestSound: (VolumeLevel) -> Unit
) {
    Column {
        SettingsSectionHeader(title = stringResource(MR.strings.alerts_header_volume_level))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            alertVolumeLevel.values.forEach { volumeLevel ->
                val selected = alertVolumeLevel.current == volumeLevel

                FilterChip(
                    selected = selected,
                    onClick = {
                        modify(alertVolumeLevel.copy(current = volumeLevel))
                    },
                    label = {
                        Text(
                            text = volumeLevel.levelName,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }
        }
        SimpleListItem(
            imageVector = Icons.Default.PlayCircle,
            text = stringResource(MR.strings.alerts_play_test_audio),
            onClick = { playTestSound(alertVolumeLevel.current) }
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun VoiceLevelSectionPreview() = GrodnoRoadsM3ThemePreview {
    VoiceLevelSection(
        alertVolumeLevel = AlertVolumeLevel(),
        modify = {},
        playTestSound = {}
    )
}