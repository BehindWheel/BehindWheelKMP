package com.egoriku.grodnoroads.screen.map.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
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
                "ДТП" to MapEventType.CarCrash,
                "Пробка" to MapEventType.TrafficJam,
                "Сломалась машина" to MapEventType.RoadIncident,
                "Ремонт дороги" to MapEventType.RoadIncident,
                "Не работают светофоры" to MapEventType.RoadIncident,
                "Дикие животные" to MapEventType.WildAnimals
            )
        )
    }
    CommonReportDialog(
        titleRes = R.string.dialog_incident,
        actions = actions.keys.toList(),
        onClose = onClose,
        onSelected = { index, inputText ->
            val pair = actions.toList()[index]
            onSend(
                pair.second,
                pair.first,
                inputText
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