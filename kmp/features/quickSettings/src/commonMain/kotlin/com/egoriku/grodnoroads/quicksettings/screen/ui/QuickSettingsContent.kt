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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_filter
import com.egoriku.grodnoroads.compose.resources.ic_moon
import com.egoriku.grodnoroads.compose.resources.ic_notification
import com.egoriku.grodnoroads.compose.resources.ic_traffic_jam
import com.egoriku.grodnoroads.compose.resources.quick_settings_app_theme
import com.egoriku.grodnoroads.compose.resources.quick_settings_header
import com.egoriku.grodnoroads.compose.resources.quick_settings_markers_filtering
import com.egoriku.grodnoroads.compose.resources.quick_settings_traffic_conditions
import com.egoriku.grodnoroads.compose.resources.quick_settings_voice_alerts
import com.egoriku.grodnoroads.foundation.core.CenterVerticallyRow
import com.egoriku.grodnoroads.foundation.core.HorizontalScrollableRow
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
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
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun QuickSettingsContent(
    quickSettingsState: QuickSettingsState,
    onChanged: (QuickSettingsPref) -> Unit
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(Res.string.quick_settings_header),
            style = MaterialTheme.typography.headlineSmall
        )
        VerticalSpacer(26.dp)
        AppearanceSection(
            appTheme = quickSettingsState.appTheme,
            onChanged = onChanged
        )
        VerticalSpacer(24.dp)
        FilteringSection(
            markerFiltering = quickSettingsState.markerFiltering,
            onChanged = onChanged
        )
        VerticalSpacer(16.dp)
        SwitchSetting(
            drawableResource = Res.drawable.ic_notification,
            name = stringResource(Res.string.quick_settings_voice_alerts),
            checked = quickSettingsState.voiceAlerts.enabled,
            onCheckedChange = { onChanged(quickSettingsState.voiceAlerts.copy(enabled = it)) }
        )
        VerticalSpacer(16.dp)
        SwitchSetting(
            drawableResource = Res.drawable.ic_traffic_jam,
            name = stringResource(Res.string.quick_settings_traffic_conditions),
            checked = quickSettingsState.trafficJamOnMap.isShow,
            onCheckedChange = { onChanged(quickSettingsState.trafficJamOnMap.copy(isShow = it)) }
        )
        VerticalSpacer(32.dp)
    }
}

@Composable
private fun AppearanceSection(
    appTheme: AppTheme,
    onChanged: (QuickSettingsPref) -> Unit
) {
    BasicSection(
        drawableResource = Res.drawable.ic_moon,
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
                        onChanged(appTheme.copy(current = theme))
                    }
                )
            }
        }
    }
}

@Composable
private fun FilteringSection(
    markerFiltering: MarkerFiltering,
    onChanged: (QuickSettingsPref) -> Unit
) {
    BasicSection(
        drawableResource = Res.drawable.ic_filter,
        name = stringResource(Res.string.quick_settings_markers_filtering)
    ) {
        HorizontalScrollableRow {
            markerFiltering.values.forEach { filtering ->
                val selected = markerFiltering.current == filtering

                FilterChip(
                    selected = selected,
                    onClick = {
                        onChanged(markerFiltering.copy(current = filtering))
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
    drawableResource: DrawableResource,
    name: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CenterVerticallyRow(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(drawableResource),
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
    drawableResource: DrawableResource,
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
            painter = painterResource(drawableResource),
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

@GrodnoRoadsPreview
@Composable
private fun QuickSettingsContentPreview() = GrodnoRoadsM3ThemePreview {
    var state by rememberMutableState { QuickSettingsState() }

    QuickSettingsContent(
        quickSettingsState = state,
        onChanged = {
            state = when (it) {
                is AppTheme -> state.copy(appTheme = it)
                is MarkerFiltering -> state.copy(markerFiltering = it)
                is TrafficJamOnMap -> state.copy(trafficJamOnMap = it)
                is VoiceAlerts -> state.copy(voiceAlerts = it)
            }
        }
    )
}