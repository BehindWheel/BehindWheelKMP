package com.egoriku.grodnoroads.map.dialog

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
import androidx.compose.ui.window.DialogProperties
import com.egoriku.grodnoroads.foundation.dialog.content.DialogButton
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.map.domain.model.MapEventType.RoadIncident
import com.egoriku.grodnoroads.map.domain.model.MessageItem
import com.egoriku.grodnoroads.map.domain.model.Source
import com.egoriku.grodnoroads.map.mode.drive.alerts.common.MessageComponent
import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MarkerAlertDialog(
    reports: Reports,
    onClose: () -> Unit
) {
    AlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        ),
        onDismissRequest = onClose,
        title = {
            Text(text = reports.dialogTitle, style = MaterialTheme.typography.h6)
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

@GrodnoRoadsPreview
@Composable
private
fun PreviewMarkerAlertDialog() = GrodnoRoadsTheme {
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
            dialogTitle = "${RoadIncident.emoji} М6 выезд из города",
            markerMessage = "${RoadIncident.emoji} (12:30) М6 выезд из города",
            position = LatLng(0.0, 0.0),
            mapEventType = RoadIncident
        )
    ) {
    }
}