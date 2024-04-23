package com.egoriku.grodnoroads.quicksettings.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
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
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.*
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme.Companion.toStringResource
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.Filtering.Companion.toResource

@Composable
internal fun QuickSettingsContent(
    quickSettingsState: QuickSettingsState,
    onChanged: (QuickSettingsPref) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 20.dp),
            text = stringResource(R.string.quick_settings_header),
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
            iconRes = R.drawable.ic_notification,
            name = stringResource(R.string.quick_settings_voice_alerts),
            checked = quickSettingsState.voiceAlerts.enabled,
            onCheckedChange = { onChanged(quickSettingsState.voiceAlerts.copy(enabled = it)) }
        )
        VerticalSpacer(16.dp)
        SwitchSetting(
            iconRes = R.drawable.ic_traffic_jam,
            name = stringResource(R.string.quick_settings_traffic_conditions),
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
        iconRes = R.drawable.ic_moon,
        name = stringResource(id = R.string.quick_settings_app_theme)
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
        iconRes = R.drawable.ic_filter,
        name = stringResource(id = R.string.quick_settings_markers_filtering)
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
                            text = stringResource(id = filtering.toResource()),
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
    @DrawableRes iconRes: Int,
    name: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CenterVerticallyRow(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(iconRes),
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
    @DrawableRes iconRes: Int,
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
            painter = painterResource(iconRes),
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