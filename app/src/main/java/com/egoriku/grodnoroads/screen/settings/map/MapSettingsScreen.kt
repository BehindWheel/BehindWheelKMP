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
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.HSpacer
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.State
import com.egoriku.grodnoroads.screen.settings.map.ui.DefaultLocationSection
import com.egoriku.grodnoroads.screen.settings.map.ui.MapEventsSection
import com.egoriku.grodnoroads.screen.settings.map.ui.MapStyleSection
import com.egoriku.grodnoroads.screen.settings.map.ui.dialog.DefaultLocationDialog

@Composable
fun MapSettingsScreen(
    mapSettingsComponent: MapSettingsComponent,
    onBack: () -> Unit
) {
    val state by mapSettingsComponent.state.collectAsState(initial = State())

    DialogHandler(
        dialogState = state.mapDialogState,
        onClose = mapSettingsComponent::closeDialog,
        onResult = {
            mapSettingsComponent.modify(it)
            mapSettingsComponent.closeDialog()
        }
    )

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
            DefaultLocationSection(
                defaultCity = state.mapSettingsState.defaultCity,
                onCheckedChange = mapSettingsComponent::openDialog
            )
            MapStyleSection(
                mapStyle = state.mapSettingsState.mapStyle,
                onCheckedChange = mapSettingsComponent::modify
            )
            MapEventsSection(
                mapInfo = state.mapSettingsState.mapInfo,
                onCheckedChange = mapSettingsComponent::modify
            )
            HSpacer(dp = 48.dp)
        }
    }
}

@Composable
private fun DialogHandler(
    dialogState: MapDialogState,
    onClose: () -> Unit,
    onResult: (MapPref) -> Unit
) {
    when (dialogState) {
        is DefaultLocationDialogState -> {
            DefaultLocationDialog(
                defaultLocationState = dialogState,
                onClose = onClose,
                onResult = onResult
            )
        }
        else -> {}
    }
}