package com.egoriku.grodnoroads.guidance.screen.ui.mode

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.extensions.Uuid
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.isMediumScreenWidth
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.More
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size
import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.Alerts
import com.egoriku.grodnoroads.shared.models.MapEventType
import com.egoriku.grodnoroads.shared.models.MessageSource.Viber
import kotlinx.coroutines.delay

@Composable
fun DefaultOverlay(
    contentPadding: PaddingValues,
    isOverlayVisible: Boolean,
    isDriveMode: Boolean,
    currentSpeed: Int,
    speedLimit: Int,
    alerts: List<Alert>,
    modifier: Modifier = Modifier,
    onOpenQuickSettings: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        if (isDriveMode) {
            when {
                currentWindowAdaptiveInfoV2().windowSizeClass.isMediumScreenWidth() -> {
                    TabletOverlay(
                        contentPadding = contentPadding,
                        currentSpeed = currentSpeed,
                        speedLimit = speedLimit,
                        alerts = alerts
                    )
                }
                else -> {
                    PhoneOverlay(
                        contentPadding = contentPadding,
                        currentSpeed = currentSpeed,
                        speedLimit = speedLimit,
                        alerts = alerts
                    )
                }
            }
        }
        FadeInOutAnimatedVisibility(
            visible = isOverlayVisible,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(contentPadding)
                .align(Alignment.TopEnd)
        ) {
            PrimaryInverseCircleButton(
                onClick = onOpenQuickSettings,
                size = Size.Small
            ) {
                Icon(
                    imageVector = GrodnoRoads.Outlined.More,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun PhoneOverlay(
    contentPadding: PaddingValues,
    currentSpeed: Int,
    speedLimit: Int,
    alerts: List<Alert>
) {
    Column(
        modifier = Modifier.padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SpeedRow(
            currentSpeed = currentSpeed,
            speedLimit = speedLimit
        )
        Alerts(alerts = alerts)
    }
}

@Composable
private fun TabletOverlay(
    contentPadding: PaddingValues,
    currentSpeed: Int,
    speedLimit: Int,
    alerts: List<Alert>
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        SpeedRow(
            currentSpeed = currentSpeed,
            speedLimit = speedLimit
        )
        Alerts(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .widthIn(max = 500.dp),
            alerts = alerts
        )
    }
}

@Composable
private fun SpeedRow(
    currentSpeed: Int,
    speedLimit: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy((-8).dp)
    ) {
        CarSpeed(speed = currentSpeed)
        if (speedLimit != -1) {
            SpeedLimit(limit = speedLimit)
        }
    }
}

@Composable
private fun CarSpeed(
    speed: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(64.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = CircleShape
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = speed.toString(),
                textAlign = TextAlign.Center,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SpeedLimit(limit: Int) {
    val circles = listOf(
        remember { Animatable(initialValue = 0.4f) },
        remember { Animatable(initialValue = 0.4f) },
        remember { Animatable(initialValue = 0.4f) }
    )

    val animationDelay = 1500

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(Unit) {
            delay(timeMillis = (animationDelay / 3L) * (index + 1))

            animatable.animateTo(
                targetValue = 0.8f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Box(modifier = Modifier.size(48.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            circles.forEach { animatable ->
                drawCircle(
                    radius = size.minDimension * animatable.value,
                    alpha = 0.8f - animatable.value,
                    color = Color.Red
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color.White)
                .border(width = 3.dp, color = Color.Red, shape = CircleShape)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                text = limit.toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(widthDp = 1000, heightDp = 400)
@PreviewGrodnoRoadsDarkLight
@Composable
private fun DefaultOverlayPreview() = GrodnoRoadsM3ThemePreview {
    var limit by rememberMutableState { -1 }
    var driveMode by rememberMutableState { true }

    Column {
        DefaultOverlay(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(),
            isOverlayVisible = true,
            isDriveMode = driveMode,
            currentSpeed = 120,
            speedLimit = limit,
            alerts = listOf(
                IncidentAlert(
                    id = Uuid.random(),
                    mapEventType = MapEventType.TrafficPolice,
                    distance = 1,
                    messages = listOf(
                        MessageItem(
                            message = "Славинского Беларуснефть на скорость",
                            messageSource = Viber
                        )
                    )
                ),
                IncidentAlert(
                    id = Uuid.random(),
                    mapEventType = MapEventType.CarCrash,
                    distance = 120,
                    messages = listOf(
                        MessageItem(
                            message = "Славинского ДТП",
                            messageSource = Viber
                        )
                    )
                )
            ),
            onOpenQuickSettings = {}
        )
        HorizontalDivider()
        VerticalSpacer(8.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Button(onClick = { limit = 50 }) {
                Text(text = "Speed limit")
            }
            Button(onClick = { limit = -1 }) {
                Text(text = "Reset")
            }
            Button(onClick = { driveMode = !driveMode }) {
                Text(text = "Toggle Mode")
            }
        }
        VerticalSpacer(8.dp)
    }
}
