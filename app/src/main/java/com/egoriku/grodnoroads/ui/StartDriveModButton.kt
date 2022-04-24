package com.egoriku.grodnoroads.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R

@Composable
fun StartDriveModButton(
    modifier: Modifier = Modifier,
    onNavigationStart: () -> Unit
) {
    IconButton(
        onClick = onNavigationStart,
        modifier = modifier
            .size(80.dp)
            .padding(bottom = 16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_car),
            contentDescription = "Start Navigation"
        )
    }
}