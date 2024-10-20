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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.extensions.Uuid
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.More
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size
import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.Alerts
import com.egoriku.grodnoroads.shared.models.MapEventType
import com.egoriku.grodnoroads.shared.models.MessageSource.Viber
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay

@Composable
fun DefaultOverlay(
    contentPadding: PaddingValues,
    isOverlayVisible: Boolean,
    isDriveMode: Boolean,
    currentSpeed: Int,
    speedLimit: Int,
    alerts: ImmutableList<Alert>,
    modifier: Modifier = Modifier,
    onOpenQuickSettings: () -> Unit
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isDriveMode) {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy((-8).dp)
                ) {
                    CarSpeed(speed = currentSpeed)
                    if (speedLimit != -1) {
                        SpeedLimit(limit = speedLimit)
                    }
                }
                Alerts(alerts = alerts)
            }
        }
        FadeInOutAnimatedVisibility(
            visible = isOverlayVisible,
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            PrimaryInverseCircleButton(
                modifier = Modifier
                    .padding(contentPadding)
                    .padding(horizontal = 16.dp),
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

@PreviewGrodnoRoadsDarkLight
@Composable
private fun DefaultOverlayPreview() = GrodnoRoadsM3ThemePreview {
    var limit by rememberMutableState { -1 }

    Box {
        DefaultOverlay(
            contentPadding = PaddingValues(),
            isOverlayVisible = true,
            isDriveMode = true,
            currentSpeed = 120,
            speedLimit = limit,
            alerts = persistentListOf(
                IncidentAlert(
                    id = Uuid.random(),
                    mapEventType = MapEventType.TrafficPolice,
                    distance = 1,
                    messages = persistentListOf(
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
                    messages = persistentListOf(
                        MessageItem(
                            message = "Славинского ДТП",
                            messageSource = Viber
                        )
                    )
                )
            ),
            onOpenQuickSettings = {}
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { limit = 50 }) {
                Text(text = "Set limit")
            }
            Button(onClick = { limit = -1 }) {
                Text(text = "Reset limit")
            }
        }
    }
}
