package com.egoriku.grodnoroads.screen.map.ui.drivemode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.MapEventType.*
import com.egoriku.grodnoroads.foundation.alerts.CameraAlert
import com.egoriku.grodnoroads.foundation.alerts.IncidentAlert
import com.egoriku.grodnoroads.screen.map.MapComponent.AlertMessage

@Composable
fun Alerts(
    modifier: Modifier = Modifier,
    alertMessages: List<AlertMessage>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(alertMessages) { alert ->
            when (alert.mapEventType) {
                StationaryCamera -> CameraAlert(
                    distance = alert.distance,
                    speedLimit = alert.speedLimit,
                    painter = painterResource(id = R.drawable.ic_stationary_camera),
                    title = stringResource(R.string.alerts_stationary_camera)
                )
                MobileCamera -> CameraAlert(
                    distance = alert.distance,
                    speedLimit = alert.speedLimit,
                    painter = painterResource(id = R.drawable.ic_mobile_camera),
                    title = stringResource(R.string.alerts_mobile_camera)
                )
                TrafficPolice -> IncidentAlert(
                    title = stringResource(R.string.alerts_police),
                    distance = alert.distance,
                    message = alert.message
                )
                RoadAccident -> IncidentAlert(
                    title = stringResource(R.string.alerts_accident),
                    distance = alert.distance,
                    message = alert.message
                )
            }
        }
    }
}

@Preview
@Preview(locale = "ru")
@Composable
private fun AlertsPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Alerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 1,
                    message = "· Славинского беларуснефть на скорость",
                    speedLimit = -1,
                    mapEventType = TrafficPolice
                )
            )
        )
        Alerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 2,
                    message = "",
                    speedLimit = 60,
                    mapEventType = StationaryCamera
                )
            )
        )
        Alerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 5,
                    message = "· (15:30) Старый мост ДТП в правой полосе по направлению от кольца в центр\n· (15:45) Новый мост в левой полосе по направлению",
                    speedLimit = -1,
                    mapEventType = RoadAccident
                )
            )
        )
        Alerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 220,
                    message = "ул. Поповича",
                    speedLimit = -1,
                    mapEventType = MobileCamera
                )
            )
        )
        Alerts(
            alertMessages = listOf(
                AlertMessage(
                    distance = 220,
                    message = "ул. Поповича",
                    speedLimit = 60,
                    mapEventType = MobileCamera
                )
            )
        )
    }
}