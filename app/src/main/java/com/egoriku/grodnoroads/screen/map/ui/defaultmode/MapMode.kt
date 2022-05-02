package com.egoriku.grodnoroads.screen.map.ui.defaultmode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.screen.map.ui.defaultmode.ui.StartDriveModButton

@Composable
fun MapMode(startNavigation: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        StartDriveModButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onLocationEnabled = {
                startNavigation()
            },
            onLocationDisabled = {

            }
        )
    }
}