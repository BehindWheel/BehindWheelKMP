package com.egoriku.grodnoroads.map.mode.drive.action

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CloseAction(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = 5.dp,
        onClick = onClick
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Icon(
                imageVector = imageVector,
                contentDescription = "Close"
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun CloseActionPreview() {
    GrodnoRoadsTheme {
        CloseAction(imageVector = Icons.Default.Close) {}
    }
}