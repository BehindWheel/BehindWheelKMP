package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.alerts_mobile_camera
import com.egoriku.grodnoroads.compose.resources.alerts_stationary_camera
import com.egoriku.grodnoroads.compose.resources.camera_alerts_plurals_distance
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.colored.MediumSpeedCamera
import com.egoriku.grodnoroads.foundation.icons.colored.MobileCamera
import com.egoriku.grodnoroads.foundation.icons.colored.StationaryCamera
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.guidance.screen.ui.foundation.SpeedLimitSign
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CameraAlert(
    distance: Int,
    speedLimit: Int,
    imageVector: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(46.dp),
                    imageVector = imageVector,
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    VerticalSpacer(4.dp)
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = pluralStringResource(
                            Res.plurals.camera_alerts_plurals_distance,
                            distance,
                            distance
                        ),
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                if (speedLimit > 0) {
                    SpeedLimitSign(limit = speedLimit, fontSize = 25.sp)
                }
            }
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun PreviewStationaryAlertPreview() = GrodnoRoadsM3ThemePreview {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CameraAlert(
            distance = 200,
            speedLimit = 60,
            imageVector = GrodnoRoads.Colored.StationaryCamera,
            title = stringResource(Res.string.alerts_stationary_camera)
        )
        CameraAlert(
            distance = 200,
            speedLimit = -1,
            imageVector = GrodnoRoads.Colored.StationaryCamera,
            title = stringResource(Res.string.alerts_stationary_camera)
        )
        CameraAlert(
            distance = 200,
            speedLimit = 60,
            imageVector = GrodnoRoads.Colored.MobileCamera,
            title = stringResource(Res.string.alerts_mobile_camera)
        )
        CameraAlert(
            distance = 200,
            speedLimit = -1,
            imageVector = GrodnoRoads.Colored.MobileCamera,
            title = stringResource(Res.string.alerts_mobile_camera)
        )
        CameraAlert(
            distance = 200,
            speedLimit = 60,
            imageVector = GrodnoRoads.Colored.MediumSpeedCamera,
            title = stringResource(Res.string.alerts_mobile_camera)
        )
        CameraAlert(
            distance = 200,
            speedLimit = -1,
            imageVector = GrodnoRoads.Colored.MediumSpeedCamera,
            title = stringResource(Res.string.alerts_mobile_camera)
        )
    }
}
