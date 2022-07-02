package com.egoriku.grodnoroads.screen.map.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.screen.map.ui.dialog.common.CommonReportDialog
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun ReportDialog(
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
                "Работают с радаром" to TrafficPolice,
                "Проверка документов" to TrafficPolice,
                "Остановили на ходу" to TrafficPolice,
                "Сидят в машине" to TrafficPolice,
                "Работают на ходу" to TrafficPolice,
                "Транспортная инспекция" to TrafficPolice,
                "Фильтр" to TrafficPolice,
                "Регулировщик" to TrafficPolice
            )
        )
    }

    CommonReportDialog(
        titleRes = R.string.dialog_report_police,
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
fun PreviewReportDialog() {
    GrodnoRoadsTheme {
        ReportDialog(onClose = {}, onSend = { _, _, _ -> })
    }
}