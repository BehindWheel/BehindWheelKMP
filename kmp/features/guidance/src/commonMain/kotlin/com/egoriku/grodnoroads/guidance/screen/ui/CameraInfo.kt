package com.egoriku.grodnoroads.guidance.screen.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.alerts_medium_speed_camera
import com.egoriku.grodnoroads.compose.resources.alerts_mobile_camera
import com.egoriku.grodnoroads.compose.resources.alerts_stationary_camera
import com.egoriku.grodnoroads.compose.resources.camera_info_last_update
import com.egoriku.grodnoroads.compose.resources.camera_info_report
import com.egoriku.grodnoroads.compose.resources.ic_car
import com.egoriku.grodnoroads.compose.resources.ic_truck
import com.egoriku.grodnoroads.compose.resources.nt_ic_medium_speed_camera_bold
import com.egoriku.grodnoroads.compose.resources.nt_ic_mobile_camera_bold
import com.egoriku.grodnoroads.compose.resources.nt_ic_stationary_camera_bold
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.Red
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import com.egoriku.grodnoroads.foundation.uikit.HorizontalSpacer
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryButton
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MediumSpeedCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.StationaryCamera
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.components.FeatureFlags
import com.egoriku.grodnoroads.shared.formatter.CameraFormatter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CameraInfo(camera: MapEvent.Camera, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Info(
            camera = camera,
            drawableResource = when (camera) {
                is StationaryCamera -> Res.drawable.nt_ic_stationary_camera_bold
                is MediumSpeedCamera -> Res.drawable.nt_ic_medium_speed_camera_bold
                is MobileCamera -> Res.drawable.nt_ic_mobile_camera_bold
            },
            cameraResource = when (camera) {
                is StationaryCamera -> Res.string.alerts_stationary_camera
                is MediumSpeedCamera -> Res.string.alerts_medium_speed_camera
                is MobileCamera -> Res.string.alerts_mobile_camera
            }
        )
        VerticalSpacer(32.dp)
    }
}

@Composable
private fun Info(
    camera: MapEvent.Camera,
    drawableResource: DrawableResource,
    cameraResource: StringResource
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row {
            Image(
                modifier = Modifier.size(64.dp),
                painter = painterResource(drawableResource),
                contentDescription = null
            )
            HorizontalSpacer(16.dp)
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(text = camera.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = stringResource(cameraResource),
                    style = MaterialTheme.typography.bodyMedium
                )
                // TODO: make separate UI model with formatted value
                val formattedDate = CameraFormatter.format(camera.updateTime)
                DisabledText(
                    text = stringResource(Res.string.camera_info_last_update, formattedDate),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
        VerticalSpacer(16.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpeedLimitGroup(
                modifier = Modifier.weight(0.5f),
                speed = camera.speedCar,
                drawableResource = Res.drawable.ic_car
            )
            SpeedLimitGroup(
                modifier = Modifier.weight(0.5f),
                speed = camera.speedTruck,
                drawableResource = Res.drawable.ic_truck
            )
        }

        if (FeatureFlags.screenReportCameraProblemEnabled) {
            VerticalSpacer(24.dp)
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.camera_info_report),
                onClick = {}
            )
        }
    }
}

@Composable
private fun SpeedLimitGroup(
    modifier: Modifier = Modifier,
    drawableResource: DrawableResource,
    speed: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(drawableResource),
            contentDescription = null
        )
        HorizontalSpacer(18.dp)
        SpeedLimit(value = speed)
    }
}

@Composable
private fun SpeedLimit(value: Int, size: Dp = 44.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .background(Color.White, shape = CircleShape)
            .border(width = 3.dp, color = Red, shape = CircleShape)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            text = value.toString()
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun CameraInfoPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CameraInfo(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            camera = StationaryCamera(
                id = "",
                name = "Гродно, ул. Магистральная",
                angle = -1.0f,
                bidirectional = false,
                updateTime = 1683234000000,
                speedCar = 100,
                speedTruck = 80,
                position = LatLng(-1.0, -1.0),
            )
        )
        CameraInfo(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            camera = MediumSpeedCamera(
                id = "",
                name = "Гродно, ул. Магистральная",
                angle = -1.0f,
                bidirectional = false,
                updateTime = 1683234000000,
                speedCar = 100,
                speedTruck = 80,
                position = LatLng(-1.0, -1.0),
            )
        )
        CameraInfo(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            camera = MobileCamera(
                id = "",
                name = "Гродно, ул. Магистральная",
                angle = -1.0f,
                bidirectional = false,
                updateTime = 1683234000000,
                speedCar = 100,
                speedTruck = 80,
                position = LatLng(-1.0, -1.0),
            )
        )
    }
}