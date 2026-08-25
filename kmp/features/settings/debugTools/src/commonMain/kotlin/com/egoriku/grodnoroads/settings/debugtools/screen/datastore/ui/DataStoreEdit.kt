package com.egoriku.grodnoroads.settings.debugtools.screen.datastore.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import com.egoriku.grodnoroads.foundation.uikit.listitem.SwitchListItem

@Composable
internal fun DataStoreEdit(
    showMapDebugOverlay: Boolean,
    onShowMapDebugOverlayChange: (Boolean) -> Unit,
    resetOnboarding: () -> Unit,
    modifier: Modifier = Modifier,
    resetReportingLimit: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SwitchListItem(
            text = "Map debug overlay",
            isChecked = showMapDebugOverlay,
            onCheckedChange = onShowMapDebugOverlayChange
        )
        PrimaryButton(
            text = "Reset onboarding",
            onClick = resetOnboarding
        )
        PrimaryButton(
            text = "Reset reporting limit",
            onClick = resetReportingLimit
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun PreviewDataStoreEditPreview() = GrodnoRoadsM3ThemePreview {
    var showMapDebugOverlay by rememberMutableState { false }

    DataStoreEdit(
        showMapDebugOverlay = showMapDebugOverlay,
        onShowMapDebugOverlayChange = { showMapDebugOverlay = it },
        resetOnboarding = {},
        resetReportingLimit = {}
    )
}
