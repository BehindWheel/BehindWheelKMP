package com.egoriku.grodnoroads.screen.settings.map

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.common.Condition.*
import com.egoriku.grodnoroads.foundation.HSpacer
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.*
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.screen.settings.map.ui.DefaultLocationSection
import com.egoriku.grodnoroads.screen.settings.map.ui.MapEventsSection
import com.egoriku.grodnoroads.screen.settings.map.ui.MapStyleSection
import com.egoriku.grodnoroads.screen.settings.map.ui.dialog.DefaultLocationDialog

@Composable
fun MapSettingsScreen(
    mapSettingsComponent: MapSettingsComponent,
    onBack: () -> Unit
) {
    val state by mapSettingsComponent.mapSettingsState.collectAsState(initial = Loading)

    Scaffold(
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_map),
                onBack = onBack
            )
        }
    ) {
        Crossfade(targetState = state) {
            when (it) {
                is Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                is Loaded -> {
                    LoadedState(
                        mapSettingState = it.data,
                        mapSettingsComponent = mapSettingsComponent
                    )
                }
                is Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(text = "Error loading screen")
                    }
                }
            }
        }
    }
}


@Composable
private fun LoadedState(
    mapSettingState: MapSettingState,
    mapSettingsComponent: MapSettingsComponent
) {
    DialogHandler(
        dialogState = mapSettingState.mapDialogState,
        onClose = mapSettingsComponent::closeDialog,
        onResult = {
            mapSettingsComponent.modify(it)
            mapSettingsComponent.closeDialog()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        DefaultLocationSection(
            locationInfo = mapSettingState.mapSettings.locationInfo,
            onCheckedChange = mapSettingsComponent::openDialog
        )
        MapStyleSection(
            mapStyle = mapSettingState.mapSettings.mapStyle,
            onCheckedChange = mapSettingsComponent::modify
        )
        MapEventsSection(
            mapInfo = mapSettingState.mapSettings.mapInfo,
            onCheckedChange = mapSettingsComponent::modify
        )
        HSpacer(dp = 48.dp)
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