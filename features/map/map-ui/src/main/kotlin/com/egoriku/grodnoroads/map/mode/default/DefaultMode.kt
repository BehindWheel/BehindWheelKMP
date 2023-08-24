package com.egoriku.grodnoroads.map.mode.default

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.CircleButton
import com.egoriku.grodnoroads.map.domain.model.ReportType
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
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CircleButton(onClick = { report(TrafficPolice) }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_pin_location),
                    contentDescription = null
                )
            }
            PermissionButton(
                onLocationEnabled = onLocationEnabled,
                onLocationDisabled = onLocationDisabled
            )
        }
    }
}