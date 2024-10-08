package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.alerts_car_crash
import com.egoriku.grodnoroads.compose.resources.alerts_incident
import com.egoriku.grodnoroads.compose.resources.alerts_medium_speed_camera
import com.egoriku.grodnoroads.compose.resources.alerts_mobile_camera
import com.egoriku.grodnoroads.compose.resources.alerts_stationary_camera
import com.egoriku.grodnoroads.compose.resources.alerts_traffic_jam
import com.egoriku.grodnoroads.compose.resources.alerts_traffic_police
import com.egoriku.grodnoroads.compose.resources.alerts_unsupported_message
import com.egoriku.grodnoroads.compose.resources.alerts_wild_animals
import com.egoriku.grodnoroads.extensions.Uuid
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.colored.MediumSpeedCamera
import com.egoriku.grodnoroads.foundation.icons.colored.MobileCamera
import com.egoriku.grodnoroads.foundation.icons.colored.StationaryCamera
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.guidance.domain.model.Alert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.CameraAlert
import com.egoriku.grodnoroads.guidance.domain.model.Alert.IncidentAlert
import com.egoriku.grodnoroads.guidance.domain.model.CameraType.MediumSpeedCamera
import com.egoriku.grodnoroads.guidance.domain.model.CameraType.MobileCamera
import com.egoriku.grodnoroads.guidance.domain.model.CameraType.StationaryCamera
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.shared.models.MapEventType.CarCrash
import com.egoriku.grodnoroads.shared.models.MapEventType.RoadIncident
import com.egoriku.grodnoroads.shared.models.MapEventType.TrafficJam
import com.egoriku.grodnoroads.shared.models.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.shared.models.MapEventType.WildAnimals
import com.egoriku.grodnoroads.shared.models.MessageSource.Viber
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Alerts(
    modifier: Modifier = Modifier,
    alerts: ImmutableList<Alert>
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
    ) {
        items(items = alerts, key = { it.id }) { alert ->
            when (alert) {
                is IncidentAlert -> {
                    val title = when (alert.mapEventType) {
                        RoadIncident -> stringResource(Res.string.alerts_incident)
                        TrafficPolice -> stringResource(Res.string.alerts_traffic_police)
                        CarCrash -> stringResource(Res.string.alerts_car_crash)
                        TrafficJam -> stringResource(Res.string.alerts_traffic_jam)
                        WildAnimals -> stringResource(Res.string.alerts_wild_animals)
                        else -> stringResource(Res.string.alerts_unsupported_message)
                    }

                    IncidentAlert(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing,
                            )
                        ),
                        emoji = alert.mapEventType.emoji,
                        title = title,
                        distance = alert.distance,
                        messages = alert.messages
                    )
                }

                is CameraAlert -> {
                    val title = when (alert.cameraType) {
                        StationaryCamera -> stringResource(Res.string.alerts_stationary_camera)
                        MobileCamera -> stringResource(Res.string.alerts_mobile_camera)
                        MediumSpeedCamera -> stringResource(Res.string.alerts_medium_speed_camera)
                    }
                    val icon = when (alert.cameraType) {
                        StationaryCamera -> GrodnoRoads.Colored.StationaryCamera
                        MobileCamera -> GrodnoRoads.Colored.MobileCamera
                        MediumSpeedCamera -> GrodnoRoads.Colored.MediumSpeedCamera
                    }
                    CameraAlert(
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearOutSlowInEasing,
                            )
                        ),
                        distance = alert.distance,
                        speedLimit = alert.speedLimit,
                        imageVector = icon,
                        title = title
                    )
                }
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun AlertsPreview() = GrodnoRoadsM3ThemePreview {
    Alerts(
        alerts = persistentListOf(
            IncidentAlert(
                mapEventType = TrafficPolice,
                distance = 1,
                id = Uuid.random(),
                messages = persistentListOf(
                    MessageItem(
                        message = "Славинского беларуснефть на скорость",
                        messageSource = Viber
                    )
                )
            ),
            CameraAlert(
                id = Uuid.random(),
                distance = 2,
                speedLimit = 60,
                cameraType = StationaryCamera
            ),
            IncidentAlert(
                id = Uuid.random(),
                distance = 5,
                messages = persistentListOf(
                    MessageItem(
                        message = "(15:30) Старый мост ДТП в правой полосе по направлению от кольца в центр",
                        messageSource = Viber
                    ),
                    MessageItem(
                        message = "(15:45) Новый мост в левой полосе по направлению",
                        messageSource = Viber
                    )
                ),
                mapEventType = RoadIncident
            ),
            CameraAlert(
                id = Uuid.random(),
                distance = 220,
                speedLimit = -1,
                cameraType = MobileCamera
            ),
            CameraAlert(
                id = Uuid.random(),
                distance = 220,
                speedLimit = 60,
                cameraType = MobileCamera
            )
        )
    )
}