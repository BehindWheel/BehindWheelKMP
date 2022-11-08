package com.egoriku.grodnoroads.map.mode.drive.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.HSpacer
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.map.domain.model.MapEventType
import com.egoriku.grodnoroads.map.domain.model.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.map.domain.model.MessageItem
import com.egoriku.grodnoroads.map.domain.model.Source
import com.egoriku.grodnoroads.map.mode.drive.alerts.common.MessageComponent
import com.egoriku.grodnoroads.resources.R
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IncidentAlert(
    emoji: String,
    title: String,
    distance: Int,
    messages: ImmutableList<MessageItem>
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 5.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = buildString {
                        append(emoji)
                        append(" ")
                        append(title)
                    },
                    style = MaterialTheme.typography.body1,
                )
                HSpacer(dp = 4.dp)
                Text(
                    text = pluralStringResource(
                        R.plurals.camera_alerts_plurals_distance,
                        distance,
                        distance
                    ),
                    style = MaterialTheme.typography.body2
                )
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            MessageComponent(messages = messages)
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewIncidentAlert() = GrodnoRoadsTheme {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        IncidentAlert(
            emoji = MapEventType.RoadIncident.emoji,
            title = stringResource(R.string.alerts_incident),
            distance = 200,
            messages = persistentListOf(
                MessageItem(
                    message = "(15:30) Старый мост ДТП в правой полосе по направлению от кольца в центр",
                    source = Source.App
                ),
                MessageItem(
                    message = "(15:45) Новый мост в левой полосе по направлению",
                    source = Source.Viber
                ),
                MessageItem(message = "(15:50) Новый мост", source = Source.Telegram)
            )
        )
        IncidentAlert(
            emoji = TrafficPolice.emoji,
            title = stringResource(R.string.alerts_traffic_police),
            distance = 350,
            messages = persistentListOf(
                MessageItem(
                    message = "(15:30) Славинского беларуснефть на скорость",
                    source = Source.App
                )
            )
        )
    }
}