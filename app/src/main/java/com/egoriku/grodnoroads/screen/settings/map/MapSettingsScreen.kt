package com.egoriku.grodnoroads.screen.settings.map

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.State
import com.egoriku.grodnoroads.screen.settings.map.ui.MapEventsSection
import com.egoriku.grodnoroads.screen.settings.map.ui.MapStyleSection

@Composable
fun MapSettingsScreen(
    mapSettingsComponent: MapSettingsComponent,
    onBack: () -> Unit
) {
    val state by mapSettingsComponent.state.collectAsState(initial = State())

    Scaffold(
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_map),
                onBack = onBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MapStyleSection(
                mapStyle = state.mapSettingsState.mapStyle,
                onCheckedChange = mapSettingsComponent::onCheckedChanged
            )
            MapEventsSection(
                mapInfo = state.mapSettingsState.mapInfo,
                onCheckedChange = mapSettingsComponent::onCheckedChanged
            )
        }
    }
}
