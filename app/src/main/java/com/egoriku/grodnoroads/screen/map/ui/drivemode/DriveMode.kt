package com.egoriku.grodnoroads.screen.map.ui.drivemode

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.screen.map.ui.drivemode.action.CloseAction
import com.egoriku.grodnoroads.screen.map.ui.drivemode.action.ReportAction

@Composable
fun DriveMode(
    stopDrive: () -> Unit,
    reportPolice: () -> Unit,
    reportAccident: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
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
            painter = painterResource(id = R.drawable.ic_close),
            onClick = stopDrive
        )
    }
}

@Preview
@Composable
private fun DriveModePReview() {
    DriveMode(
        stopDrive = {},
        reportPolice = {},
        reportAccident = {}
    )
}