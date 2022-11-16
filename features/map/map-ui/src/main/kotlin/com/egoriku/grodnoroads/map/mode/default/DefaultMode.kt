package com.egoriku.grodnoroads.map.mode.default

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.map.foundation.StartDriveModeButton

@Composable
fun DefaultMode(
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        StartDriveModeButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onLocationEnabled = onLocationEnabled,
            onLocationDisabled = onLocationDisabled
        )
    }
}