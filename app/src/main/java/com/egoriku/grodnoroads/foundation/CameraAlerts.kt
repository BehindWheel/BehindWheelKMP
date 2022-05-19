package com.egoriku.grodnoroads.foundation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.EventType
import com.egoriku.grodnoroads.domain.model.EventType.*
import com.egoriku.grodnoroads.screen.map.MapComponent.AlertMessage

@Composable
fun CameraAlerts(
    modifier: Modifier = Modifier,
    alertMessages: List<AlertMessage>
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(alertMessages) { message ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                elevation = 5.dp
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            MessageHeader(
                                eventType = message.eventType,
                                distance = message.distance
                            )
                        }

                        if (message.speedLimit > 0) {
                            SpeedLimitSign(limit = message.speedLimit)
                        }
                    }
                    Messages(message)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MessageHeader(
    eventType: EventType,
    distance: Int
) {
    val title = when (eventType) {
        StationaryCamera -> stringResource(R.string.camera_alerts_stationary_camera)
        MobileCamera -> stringResource(R.string.camera_alerts_mobile_camera)
        Accident -> stringResource(R.string.camera_alerts_accident)
        Police -> stringResource(R.string.camera_alerts_police)
    }
    Text(text = title, style = MaterialTheme.typography.body1)
    HSpacer(dp = 4.dp)
    Text(
        text = pluralStringResource(R.plurals.camera_alerts_plurals_distance, distance, distance),
        style = MaterialTheme.typography.body2
    )
}

@Composable
private fun Messages(alertMessage: AlertMessage) {
    if (alertMessage.message.isNotEmpty()) {
        Text(
            text = alertMessage.message,
            modifier = Modifier.padding(top = 16.dp),
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview
@Preview(locale = "ru")
@Composable
fun CameraAlertsPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CameraAlerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 1,
                    message = "· Славинского беларуснефть на скорость",
                    speedLimit = -1,
                    eventType = Police
                )
            )
        )
        CameraAlerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 2,
                    message = "",
                    speedLimit = 60,
                    eventType = StationaryCamera
                )
            )
        )
        CameraAlerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 5,
                    message = "· Старый мост ДТП в правой полосе по направлению от кольца в центр",
                    speedLimit = -1,
                    eventType = Accident
                )
            )
        )
        CameraAlerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 220,
                    message = "",
                    speedLimit = 60,
                    eventType = MobileCamera
                )
            )
        )
    }
}