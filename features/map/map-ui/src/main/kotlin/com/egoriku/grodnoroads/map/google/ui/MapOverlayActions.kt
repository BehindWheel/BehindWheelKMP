package com.egoriku.grodnoroads.map.google.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.ActionButton
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview

@Composable
fun MapOverlayActions(
    modifier: Modifier = Modifier,
    zoomIn: () -> Unit,
    zoomOut: () -> Unit,
    zoomToCurrentLocation: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        GroupedActionsColumn {
            ActionIcon(imageVector = Icons.Default.Add, onClick = zoomIn)
            ActionIcon(imageVector = Icons.Default.Remove, onClick = zoomOut)
        }
        // TODO: Use icon with rotation
        ActionButton(
            imageVector = Icons.Default.Navigation,
            onClick = zoomToCurrentLocation,
            rotation = 45f
        )
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun MapOverlayActionsPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(16.dp)) {
        MapOverlayActions(zoomIn = {}, zoomOut = {}, zoomToCurrentLocation = {})
    }
}