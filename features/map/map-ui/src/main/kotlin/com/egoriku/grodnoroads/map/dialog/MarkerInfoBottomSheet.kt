package com.egoriku.grodnoroads.map.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.rememberSheetCloseBehaviour
import com.egoriku.grodnoroads.foundation.uikit.button.TextButton
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.map.domain.model.MapEventType.RoadIncident
import com.egoriku.grodnoroads.map.domain.model.MessageItem
import com.egoriku.grodnoroads.map.domain.model.Source
import com.egoriku.grodnoroads.map.mode.drive.alerts.common.MessageRow
import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarkerInfoBottomSheet(
    reports: Reports,
    onClose: () -> Unit
) {
    val sheetCloseBehaviour = rememberSheetCloseBehaviour(onCancel = onClose)

    BasicModalBottomSheet(
        sheetState = sheetCloseBehaviour.sheetState,
        onCancel = onClose,
        content = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = reports.dialogTitle,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(reports.messages) {
                    MessageRow(messageItem = it)
                }
            }
            Divider()
        },
        footer = {
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                id = R.string.ok,
                onClick = { sheetCloseBehaviour.cancel() }
            )
        }
    )
}

@GrodnoRoadsPreview
@Composable
private
fun PreviewMarkerInfoBottomSheet() = GrodnoRoadsM3ThemePreview {
    MarkerInfoBottomSheet(
        reports = Reports(
            messages = persistentListOf(
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