package com.egoriku.grodnoroads.quicksettings.screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.quick_settings_app_theme
import com.egoriku.grodnoroads.compose.resources.quick_settings_header
import com.egoriku.grodnoroads.compose.resources.quick_settings_markers_filtering
import com.egoriku.grodnoroads.compose.resources.quick_settings_traffic_conditions
import com.egoriku.grodnoroads.compose.resources.quick_settings_voice_alerts
import com.egoriku.grodnoroads.foundation.core.CenterVerticallyRow
import com.egoriku.grodnoroads.foundation.core.HorizontalScrollableRow
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Filter
import com.egoriku.grodnoroads.foundation.icons.outlined.Moon
import com.egoriku.grodnoroads.foundation.icons.outlined.Notification
import com.egoriku.grodnoroads.foundation.icons.outlined.TrafficJam
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.foundation.uikit.Switch
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer
import com.egoriku.grodnoroads.quicksettings.domain.model.QuickSettingsState
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.AppTheme
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.MarkerFiltering
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.TrafficJamOnMap
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.VoiceAlerts
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun QuickSettingsContent(
    quickSettingsState: QuickSettingsState,
    modifier: Modifier = Modifier,
    onChange: (QuickSettingsPref) -> Unit
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(Res.string.quick_settings_header),
            style = MaterialTheme.typography.headlineSmall
        )
        VerticalSpacer(26.dp)
        AppearanceSection(
            appTheme = quickSettingsState.appTheme,
            onChange = onChange
        )
        VerticalSpacer(24.dp)
        FilteringSection(
            markerFiltering = quickSettingsState.markerFiltering,
            onChange = onChange
        )
        VerticalSpacer(16.dp)
        SwitchSetting(
            imageVector = GrodnoRoads.Outlined.Notification,
            name = stringResource(Res.string.quick_settings_voice_alerts),
            checked = quickSettingsState.voiceAlerts.enabled,
            onCheckedChange = { onChange(quickSettingsState.voiceAlerts.copy(enabled = it)) }
        )
        VerticalSpacer(16.dp)
        SwitchSetting(
            imageVector = GrodnoRoads.Outlined.TrafficJam,
            name = stringResource(Res.string.quick_settings_traffic_conditions),
            checked = quickSettingsState.trafficJamOnMap.isShow,
            onCheckedChange = { onChange(quickSettingsState.trafficJamOnMap.copy(isShow = it)) }
        )
        VerticalSpacer(32.dp)
    }
}

@Composable
private fun AppearanceSection(
    appTheme: AppTheme,
    onChange: (QuickSettingsPref) -> Unit
) {
    BasicSection(
        imageVector = GrodnoRoads.Outlined.Moon,
        name = stringResource(Res.string.quick_settings_app_theme)
    ) {
        HorizontalScrollableRow {
            appTheme.values.forEach { theme ->
                val selected = appTheme.current == theme

                FilterChip(
                    label = {
                        Text(
                            text = stringResource(theme.toStringResource()),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    selected = selected,
                    onClick = {
                        onChange(appTheme.copy(current = theme))
                    }
                )
            }
        }
    }
}

@Composable
private fun FilteringSection(
    markerFiltering: MarkerFiltering,
    onChange: (QuickSettingsPref) -> Unit
) {
    BasicSection(
        imageVector = GrodnoRoads.Outlined.Filter,
        name = stringResource(Res.string.quick_settings_markers_filtering)
    ) {
        HorizontalScrollableRow {
            markerFiltering.values.forEach { filtering ->
                val selected = markerFiltering.current == filtering

                FilterChip(
                    selected = selected,
                    onClick = {
                        onChange(markerFiltering.copy(current = filtering))
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

@Composable
private fun BasicSection(
    imageVector: ImageVector,
    name: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CenterVerticallyRow(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = imageVector,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                contentDescription = null
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium
            )
        }
        content()
    }
}

@Composable
private fun SwitchSetting(
    imageVector: ImageVector,
    name: String,
    checked: Boolean,
    paddingValues: PaddingValues = PaddingValues(horizontal = 20.dp),
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = checked,
                role = Role.Switch,
                onValueChange = onCheckedChange
            )
            .padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = name,
            style = MaterialTheme.typography.titleMedium
        )
        WeightSpacer()
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@PreviewGrodnoRoads
@Composable
private fun QuickSettingsContentPreview() = GrodnoRoadsM3ThemePreview {
    var state by rememberMutableState { QuickSettingsState() }

    QuickSettingsContent(
        quickSettingsState = state,
        onChange = {
            state = when (it) {
                is AppTheme -> state.copy(appTheme = it)
                is MarkerFiltering -> state.copy(markerFiltering = it)
                is TrafficJamOnMap -> state.copy(trafficJamOnMap = it)
                is VoiceAlerts -> state.copy(voiceAlerts = it)
            }
        }
    )
}
