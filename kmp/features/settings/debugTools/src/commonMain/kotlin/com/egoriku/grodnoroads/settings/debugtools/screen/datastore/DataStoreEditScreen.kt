package com.egoriku.grodnoroads.settings.debugtools.screen.datastore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.settings.debugtools.domain.datastore.DataStoreEditComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.datastore.DataStoreEditComponentPreview
import com.egoriku.grodnoroads.settings.debugtools.screen.datastore.ui.DataStoreEdit

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DataStoreEditScreen(
    dataStoreEditComponent: DataStoreEditComponent,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    val showMapDebugOverlay by dataStoreEditComponent.showMapDebugOverlay.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            SettingsTopBar(
                title = "DataStore",
                onBack = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        DataStoreEdit(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            showMapDebugOverlay = showMapDebugOverlay,
            onShowMapDebugOverlayChange = dataStoreEditComponent::setShowMapDebugOverlay,
            resetOnboarding = dataStoreEditComponent::resetOnboarding,
            resetReportingLimit = dataStoreEditComponent::resetReportingLimit
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DataStoreEditScreenPreview() = GrodnoRoadsM3ThemePreview {
    DataStoreEditScreen(
        dataStoreEditComponent = DataStoreEditComponentPreview(),
        onBack = {}
    )
}
