package com.egoriku.grodnoroads.map.mode.drive.action

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.CircleButton
import com.egoriku.grodnoroads.foundation.CircleButtonDefaults
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.surfaceSurfaceVariant
import com.egoriku.grodnoroads.resources.R

@Composable
fun ReportAction(
    modifier: Modifier = Modifier,
    drawableId: Int,
    onClick: () -> Unit
) {
    CircleButton(
        modifier = modifier,
        colors = CircleButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceSurfaceVariant
        ),
        onClick = onClick
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp),
            painter = painterResource(id = drawableId),
            contentDescription = ""
        )
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