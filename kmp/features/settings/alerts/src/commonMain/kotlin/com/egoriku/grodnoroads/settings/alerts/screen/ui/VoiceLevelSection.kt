package com.egoriku.grodnoroads.settings.alerts.screen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.alerts_header_volume_level
import com.egoriku.grodnoroads.compose.resources.alerts_play_test_audio
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.core.HorizontalScrollableRow
import com.egoriku.grodnoroads.foundation.core.LocalPlatform
import com.egoriku.grodnoroads.foundation.core.Platform
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Play
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.foundation.uikit.listitem.SimpleListItem
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent.AlertsPref.AlertVolumeLevel
import com.egoriku.grodnoroads.shared.persistent.alert.VolumeLevel
import org.jetbrains.compose.resources.stringResource

@Composable
fun VoiceLevelSection(
    alertVolumeLevel: AlertVolumeLevel,
    modify: (AlertsPref) -> Unit,
    modifier: Modifier = Modifier,
    playTestSound: (VolumeLevel) -> Unit
) {
    Column(modifier = modifier) {
        if (LocalPlatform.current == Platform.Android) {
            SettingsSectionHeader(title = stringResource(Res.string.alerts_header_volume_level))
            HorizontalScrollableRow {
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
        }
        SimpleListItem(
            imageVector = GrodnoRoads.Outlined.Play,
            text = stringResource(Res.string.alerts_play_test_audio),
            onClick = { playTestSound(alertVolumeLevel.current) }
        )
    }
}

@PreviewGrodnoRoads
@Composable
private fun VoiceLevelSectionPreview() = GrodnoRoadsM3ThemePreview {
    VoiceLevelSection(
        alertVolumeLevel = AlertVolumeLevel(),
        modify = {},
        playTestSound = {}
    )
}
