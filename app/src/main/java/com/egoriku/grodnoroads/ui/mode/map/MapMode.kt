package com.egoriku.grodnoroads.ui.mode.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.ui.StartDriveModButton

@Composable
fun MapMode(startNavigation: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        StartDriveModButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onStartNavigation = {
                logD("Start Navigation")
                startNavigation()
            }
        )
    }
}