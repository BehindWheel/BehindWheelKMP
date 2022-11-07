package com.egoriku.grodnoroads.map.mode.drive.action

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.resources.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportAction(
    modifier: Modifier = Modifier,
    drawableId: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        elevation = 5.dp,
        onClick = onClick
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(id = drawableId),
                contentDescription = ""
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun ReportActionPreview() {
    GrodnoRoadsTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ReportAction(drawableId = R.drawable.ic_traffic_police) {}
            ReportAction(drawableId = R.drawable.ic_warning) {}
        }
    }
}