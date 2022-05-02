package com.egoriku.grodnoroads.screen.map.ui.defaultmode.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.PermissionButton

@Composable
fun StartDriveModButton(
    modifier: Modifier = Modifier,
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit
) {
    PermissionButton(
        modifier = modifier
            .size(80.dp)
            .padding(bottom = 16.dp),
        onLocationEnabled = onLocationEnabled,
        onLocationDisabled = onLocationDisabled
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_car),
            contentDescription = null
        )
    }
}