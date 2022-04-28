package com.egoriku.grodnoroads.screen.map.defaultmode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.screen.map.defaultmode.ui.StartDriveModButton

@Composable
fun MapMode(startNavigation: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        StartDriveModButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onStartNavigation = {
                startNavigation()
            }
        )
    }
}