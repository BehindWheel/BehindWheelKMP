package com.egoriku.grodnoroads.map.mode.default

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.CircleButton
import com.egoriku.grodnoroads.map.domain.model.ReportType
import com.egoriku.grodnoroads.map.domain.model.ReportType.RoadIncident
import com.egoriku.grodnoroads.map.domain.model.ReportType.TrafficPolice
import com.egoriku.grodnoroads.map.foundation.PermissionButton
import com.egoriku.grodnoroads.resources.R

@Composable
fun DefaultMode(
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit,
    report: (ReportType) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ActionsRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            onLocationEnabled = onLocationEnabled,
            onLocationDisabled = onLocationDisabled,
            report = report,
        )
    }
}

@Composable
private fun ActionsRow(
    modifier: Modifier = Modifier,
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit,
    report: (ReportType) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        CircleButton(onClick = { report(TrafficPolice) }) {
            Image(
                modifier = Modifier.size(32.dp),
                painter = painterResource(R.drawable.ic_traffic_police),
                contentDescription = ""
            )
        }
        PermissionButton(
            onLocationEnabled = onLocationEnabled,
            onLocationDisabled = onLocationDisabled
        ) {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(id = R.drawable.ic_car),
                contentDescription = null
            )
        }
        CircleButton(onClick = { report(RoadIncident) }) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.ic_warning),
                contentDescription = ""
            )
        }
    }
}