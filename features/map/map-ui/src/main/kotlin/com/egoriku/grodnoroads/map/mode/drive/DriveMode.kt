package com.egoriku.grodnoroads.map.mode.drive

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.KeepScreenOn
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.map.domain.model.Alert
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.foundation.CurrentSpeedRect
import com.egoriku.grodnoroads.map.mode.drive.action.CloseAction
import com.egoriku.grodnoroads.map.mode.drive.action.ReportAction
import com.egoriku.grodnoroads.map.mode.drive.alerts.Alerts
import com.egoriku.grodnoroads.resources.R

@Composable
fun DriveMode(
    alerts: List<Alert>,
    lastLocation: LastLocation,
    stopDrive: () -> Unit,
    reportPolice: () -> Unit,
    reportIncident: () -> Unit
) {
    KeepScreenOn()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentSpeedRect(
                modifier = Modifier.statusBarsPadding(),
                speed = lastLocation.speed.toString()
            )
            Alerts(alerts = alerts)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            ReportAction(
                painter = painterResource(id = R.drawable.ic_traffic_police),
                onClick = reportPolice
            )
            ReportAction(
                painter = painterResource(id = R.drawable.ic_warning),
                onClick = reportIncident
            )
        }
        CloseAction(
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
private fun DriveModePReview() = GrodnoRoadsTheme {
    DriveMode(
        alerts = emptyList(),
        lastLocation = LastLocation.None,
        stopDrive = {},
        reportPolice = {},
        reportIncident = {}
    )
}