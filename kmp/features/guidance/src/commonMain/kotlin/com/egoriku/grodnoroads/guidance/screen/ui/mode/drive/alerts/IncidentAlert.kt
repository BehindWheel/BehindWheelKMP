package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.alerts_incident
import com.egoriku.grodnoroads.compose.resources.alerts_traffic_police
import com.egoriku.grodnoroads.compose.resources.camera_alerts_plurals_distance
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.common.MessageComponent
import com.egoriku.grodnoroads.shared.models.MapEventType.RoadIncident
import com.egoriku.grodnoroads.shared.models.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.shared.models.MessageSource.App
import com.egoriku.grodnoroads.shared.models.MessageSource.Telegram
import com.egoriku.grodnoroads.shared.models.MessageSource.Viber
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun IncidentAlert(
    emoji: String,
    title: String,
    distance: Int,
    messages: ImmutableList<MessageItem>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = buildString {
                    append(emoji)
                    append(" ")
                    append(title)
                },
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = pluralStringResource(
                    Res.plurals.camera_alerts_plurals_distance,
                    distance,
                    distance
                ),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        HorizontalDivider()
        MessageComponent(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            messages = messages
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewIncidentAlert() = GrodnoRoadsM3ThemePreview {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        IncidentAlert(
            emoji = RoadIncident.emoji,
            title = stringResource(Res.string.alerts_incident),
            distance = 200,
            messages = persistentListOf(
                MessageItem(
                    message = "(15:30) Старый мост ДТП в правой полосе по направлению от кольца в центр",
                    messageSource = App
                ),
                MessageItem(
                    message = "(15:45) Новый мост в левой полосе по направлению",
                    messageSource = Viber
                ),
                MessageItem(message = "(15:50) Новый мост", messageSource = Telegram)
            )
        )
        IncidentAlert(
            emoji = TrafficPolice.emoji,
            title = stringResource(Res.string.alerts_traffic_police),
            distance = 350,
            messages = persistentListOf(
                MessageItem(
                    message = "(15:30) Славинского беларуснефть на скорость",
                    messageSource = App
                )
            )
        )
    }
}