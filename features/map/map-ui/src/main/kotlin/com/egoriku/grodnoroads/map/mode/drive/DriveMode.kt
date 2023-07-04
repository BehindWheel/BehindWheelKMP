package com.egoriku.grodnoroads.map.mode.drive

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.ActionButton
import com.egoriku.grodnoroads.foundation.KeepScreenOn
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.map.mode.drive.action.ReportAction
import com.egoriku.grodnoroads.resources.R

@Composable
fun DriveMode(
    stopDrive: () -> Unit,
    reportPolice: () -> Unit,
    reportIncident: () -> Unit
) {
    KeepScreenOn()
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            ReportAction(
                drawableId = R.drawable.ic_traffic_police,
                onClick = reportPolice
            )
            ReportAction(
                drawableId = R.drawable.ic_warning,
                onClick = reportIncident
            )
        }
        ActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
            imageVector = Icons.Default.Close,
            onClick = stopDrive
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun DriveModePReview() = GrodnoRoadsM3ThemePreview {
    DriveMode(
        stopDrive = {},
        reportPolice = {},
        reportIncident = {}
    )
}