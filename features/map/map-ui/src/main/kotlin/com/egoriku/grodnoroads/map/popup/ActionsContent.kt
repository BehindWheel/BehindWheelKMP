package com.egoriku.grodnoroads.map.popup

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.foundation.uikit.Switch
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref.*
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme.Companion.toStringResource
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.Filtering.Companion.toResource

@Composable
fun ActionsContent(
    actionsState: QuickActionsState,
    onChanged: (QuickActionsPref) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AppearanceSection(
            appTheme = actionsState.appTheme,
            onChanged = onChanged
        )
        VerticalSpacer(12.dp)
        FilteringSection(
            markerFiltering = actionsState.markerFiltering,
            onChanged = onChanged
        )
        VerticalSpacer(12.dp)
        SwitchSetting(
            name = stringResource(R.string.alerts_voice_alerts),
            checked = actionsState.voiceAlerts.enabled,
            onCheckedChange = { onChanged(actionsState.voiceAlerts.copy(enabled = it)) }
        )
        SwitchSetting(
            name = stringResource(R.string.map_traffic_conditions_appearance),
            checked = actionsState.trafficJamOnMap.isShow,
            onCheckedChange = { onChanged(actionsState.trafficJamOnMap.copy(isShow = it)) }
        )
    }
}

@Composable
private fun AppearanceSection(
    appTheme: AppTheme,
    onChanged: (QuickActionsPref) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.appearance_app_theme),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            appTheme.values.forEach { theme ->
                val selected = appTheme.current == theme

                FilterChip(
                    label = {
                        Text(text = stringResource(theme.toStringResource()))
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
    onChanged: (QuickActionsPref) -> Unit
) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = stringResource(id = R.string.map_markers_filtering),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            markerFiltering.values.forEach { filtering ->
                val selected = markerFiltering.current == filtering

                FilterChip(
                    selected = selected,
                    onClick = {
                        onChanged(markerFiltering.copy(current = filtering))
                    },
                    label = {
                        Text(text = stringResource(id = filtering.toResource()))
                    }
                )
            }
        }
    }
}

@Composable
private fun SwitchSetting(
    name: String,
    checked: Boolean,
    paddingValues: PaddingValues = PaddingValues(horizontal = 16.dp),
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 44.dp)
            .toggleable(
                value = checked,
                role = Role.Switch,
                onValueChange = onCheckedChange
            )
            .padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = name, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@GrodnoRoadsPreview
@Composable
private fun ActionsContentPreview() = GrodnoRoadsM3ThemePreview {
    var state by rememberMutableState { QuickActionsState() }

    ActionsContent(
        actionsState = state,
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