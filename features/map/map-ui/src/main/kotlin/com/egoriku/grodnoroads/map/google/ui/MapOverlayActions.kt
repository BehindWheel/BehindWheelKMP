package com.egoriku.grodnoroads.map.google.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.ActionButton

@Composable
fun MapOverlayActions(
    modifier: Modifier = Modifier,
    zoomIn: () -> Unit,
    zoomOut: () -> Unit
) {
    Column(modifier = modifier.padding(end = 16.dp)) {
        ActionButton(
            imageVector = Icons.Default.Add,
            onClick = zoomIn
        )
        ActionButton(
            imageVector = Icons.Default.Remove,
            onClick = zoomOut
        )
    }
}