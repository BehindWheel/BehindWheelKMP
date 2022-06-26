package com.egoriku.grodnoroads.screen.map.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.alerts.common.MessageComponent
import com.egoriku.grodnoroads.foundation.button.DialogButton
import com.egoriku.grodnoroads.screen.map.domain.LocationState.Companion.EmptyLatLng
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.Reports
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.MessageItem
import com.egoriku.grodnoroads.screen.map.domain.Source

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MarkerAlertDialog(
    reports: Reports?,
    onClose: () -> Unit
) {
    if (reports == null) return

    AlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        ),
        onDismissRequest = onClose,
        title = {
            Text(text = reports.shortMessage, style = MaterialTheme.typography.h6)
        },
        text = {
            MessageComponent(messages = reports.messages)
        },
        buttons = {
            Column(verticalArrangement = Arrangement.Center) {
                Divider()
                DialogButton(
                    modifier = Modifier.fillMaxWidth(),
                    textResId = R.string.ok,
                    onClick = onClose
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewMarkerAlertDialog() {
    MarkerAlertDialog(
        reports = Reports(
            messages = listOf(
                MessageItem(
                    message = "(12:30) М6 выезд из города в сторону Минска сразу за заправками на скорость",
                    source = Source.App
                ),
                MessageItem(
                    message = "(12:40) м6 заправка Белорусьнефть выезд ГАИ на камеру",
                    source = Source.Telegram
                ),
                MessageItem(
                    message = "(12:41) М6 выезд стоят",
                    source = Source.Telegram
                ),
                MessageItem(
                    message = "(12:42) Выезд на М6 работают",
                    source = Source.Viber
                ),
            ),
            shortMessage = "\uD83D\uDEA7 (12:30) м6 выезд из города",
            position = EmptyLatLng,
            mapEventType = MapEventType.RoadIncident
        )
    ) {
    }
}