package com.egoriku.grodnoroads.screen.map.ui.defaultmode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.DrawerButton
import com.egoriku.grodnoroads.screen.map.ui.defaultmode.ui.StartDriveModButton

@Composable
fun MapMode(
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

        StartDriveModButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onLocationEnabled = onLocationEnabled,
            onLocationDisabled = onLocationDisabled
        )
    }
}