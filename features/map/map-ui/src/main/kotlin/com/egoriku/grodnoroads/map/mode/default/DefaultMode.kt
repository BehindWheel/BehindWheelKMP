package com.egoriku.grodnoroads.map.mode.default

import DrawerButton
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.map.foundation.StartDriveModeButton

@Composable
fun DefaultMode(
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit,
    openDrawer: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        DrawerButton(
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.TopStart)
                .statusBarsPadding(),
            onClick = openDrawer
        )

        StartDriveModeButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onLocationEnabled = onLocationEnabled,
            onLocationDisabled = onLocationDisabled
        )
    }
}