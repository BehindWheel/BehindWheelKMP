package com.egoriku.grodnoroads.screen.map.ui.drivemode.action

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportAction(
    modifier: Modifier = Modifier,
    painter: Painter,
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
                painter = painter,
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true, locale = "ru")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ReportActionPreview() {
    GrodnoRoadsTheme {
        Surface {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ReportAction(painter = painterResource(id = R.drawable.ic_traffic_police)) {}
                ReportAction(painter = painterResource(id = R.drawable.ic_warning)) {}
            }
        }
    }
}