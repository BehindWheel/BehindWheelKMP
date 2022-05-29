package com.egoriku.grodnoroads.screen.map.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.egoriku.grodnoroads.foundation.alerts.common.MessageComponent
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.Reports
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.MessageItem
import com.egoriku.grodnoroads.screen.map.domain.Source
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
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
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Divider()
                CompositionLocalProvider(
                    LocalMinimumTouchTargetEnforcement provides false,
                ) {
                    TextButton(
                        shape = RoundedCornerShape(0.dp),
                        contentPadding = PaddingValues(vertical = 8.dp),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClose
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = stringResource(id = android.R.string.ok),
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
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
            position = LatLng(0.0, 0.0),
            mapEventType = MapEventType.RoadAccident
        )
    ) {
    }
}