package com.egoriku.grodnoroads.map.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.map.R

@Composable
fun StartDriveModeButton(
    modifier: Modifier = Modifier,
    onLocationEnabled: () -> Unit,
    onLocationDisabled: () -> Unit
) {
    PermissionButton(
        modifier = modifier.padding(bottom = 16.dp),
        onLocationEnabled = onLocationEnabled,
        onLocationDisabled = onLocationDisabled
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = R.drawable.ic_car),
            contentDescription = null
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewStartDriveModButton() = GrodnoRoadsTheme {
    StartDriveModeButton(onLocationEnabled = {}, onLocationDisabled = {})
}