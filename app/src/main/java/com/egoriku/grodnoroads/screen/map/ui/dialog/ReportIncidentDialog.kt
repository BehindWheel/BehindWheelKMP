package com.egoriku.grodnoroads.screen.map.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.MapEventType.*
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
                "Ремонт дороги" to RoadIncident,
                "Не работают светофоры" to RoadIncident,
                "Дикие животные" to WildAnimals
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