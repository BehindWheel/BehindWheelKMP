package com.egoriku.grodnoroads.screen.map.ui.drivemode

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.foundation.CameraAlerts
import com.egoriku.grodnoroads.foundation.CurrentSpeed
import com.egoriku.grodnoroads.foundation.KeepScreenOn
import com.egoriku.grodnoroads.screen.map.MapComponent.AlertMessage
import com.egoriku.grodnoroads.screen.map.ui.drivemode.action.CloseAction
import com.egoriku.grodnoroads.screen.map.ui.drivemode.action.ReportAction

@Composable
fun DriveMode(
    alertMessages: List<AlertMessage>,
    location: LocationState,
    stopDrive: () -> Unit,
    reportPolice: () -> Unit,
    reportAccident: () -> Unit
) {
    KeepScreenOn()
    Box(modifier = Modifier.fillMaxSize()) {
        CurrentSpeed(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 16.dp)
                .statusBarsPadding(),
            speed = location.speed.toString()
        )
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            ReportAction(
                painter = painterResource(id = R.drawable.ic_police_car),
                onClick = reportPolice
            )
            ReportAction(
                painter = painterResource(id = R.drawable.ic_accident),
                onClick = reportAccident
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

        CameraAlerts(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding(),
            alertMessages = alertMessages
        )
    }
}

@Preview
@Composable
private fun DriveModePReview() {
    DriveMode(
        alertMessages = emptyList(),
        location = LocationState.None,
        stopDrive = {},
        reportPolice = {},
        reportAccident = {}
    )
}