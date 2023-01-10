package com.egoriku.grodnoroads.map.mode.default

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.map.foundation.PermissionButton
import com.egoriku.grodnoroads.resources.R

@Composable
fun DefaultMode(
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit,
    report: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ActionsRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
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
    report: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            modifier = Modifier.size(64.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            shape = CircleShape,
            onClick = report
        ) {
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
            Text(text = "GO", fontSize = 30.sp)
        }
        Button(
            modifier = Modifier.size(64.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            shape = CircleShape,
            onClick = { }
        ) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.ic_warning),
                contentDescription = ""
            )
        }
    }
}