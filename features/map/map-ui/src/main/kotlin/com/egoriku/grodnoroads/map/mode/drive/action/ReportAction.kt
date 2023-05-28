package com.egoriku.grodnoroads.map.mode.drive.action

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportAction(
    modifier: Modifier = Modifier,
    drawableId: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = CircleShape,
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
private fun ReportActionPreview() = GrodnoRoadsM3ThemePreview {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ReportAction(drawableId = R.drawable.ic_traffic_police) {}
        ReportAction(drawableId = R.drawable.ic_warning) {}
    }
}