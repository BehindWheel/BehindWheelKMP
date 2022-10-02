package com.egoriku.grodnoroads.screen.map.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.map.domain.model.MapEventType
import com.egoriku.grodnoroads.map.domain.model.MapEventType.CarCrash
import com.egoriku.grodnoroads.map.domain.model.MapEventType.RoadIncident
import com.egoriku.grodnoroads.map.domain.model.MapEventType.TrafficJam
import com.egoriku.grodnoroads.map.domain.model.MapEventType.WildAnimals
import com.egoriku.grodnoroads.screen.map.ui.dialog.common.CommonReportDialog
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun IncidentDialog(
    onClose: () -> Unit,
    onSend: (
        mapEvent: MapEventType,
        shortMessage: String,
        message: String
    ) -> Unit
) {
    val actions by remember {
        mutableStateOf(
            mapOf(
                "ДТП" to CarCrash,
                "Пробка" to TrafficJam,
                "Сломалась машина" to RoadIncident,
                "Дорожные работы" to RoadIncident,
                "Не работают светофоры" to RoadIncident,
                "Дикие животные" to WildAnimals,
                "Другое" to RoadIncident
            )
        )
    }
    CommonReportDialog(
        titleRes = R.string.dialog_incident,
        actions = actions.keys.toList(),
        onClose = onClose,
        onSelected = { index, inputText ->
            val pair = actions.toList()[index]

            val eventType = pair.second
            val shortMessage = pair.first

            onSend(
                eventType,
                shortMessage,
                inputText.ifEmpty { shortMessage }
            )
        }
    )
}

@Preview
@Preview(locale = "ru")
@Composable
fun PreviewIncidentDialog() {
    GrodnoRoadsTheme {
        IncidentDialog(onClose = {}, onSend = { _, _, _ -> })
    }
}