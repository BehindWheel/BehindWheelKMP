package com.egoriku.grodnoroads.specialevent.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.event_reminder_autumn_body
import com.egoriku.grodnoroads.compose.resources.event_reminder_spring_body
import com.egoriku.grodnoroads.compose.resources.ok
import com.egoriku.grodnoroads.foundation.common.ui.dialog.DialogContent
import com.egoriku.grodnoroads.foundation.common.ui.dialog.content.DialogButton
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.model.EventType.Autumn
import com.egoriku.grodnoroads.specialevent.domain.model.EventType.Spring
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialEventDialog(eventType: EventType, onClose: () -> Unit) {
    val dialogContent = when (eventType) {
        Spring -> stringResource(Res.string.event_reminder_spring_body)
        Autumn -> stringResource(Res.string.event_reminder_autumn_body)
    }

    BasicAlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = false
        ),
        onDismissRequest = onClose
    ) {
        DialogContent {
            Column(verticalArrangement = Arrangement.Center) {
                Text(modifier = Modifier.padding(16.dp), text = dialogContent)
                HorizontalDivider()
                DialogButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(Res.string.ok),
                    onClick = onClose
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewSpecialEventDialogSpring() = GrodnoRoadsM3ThemePreview {
    SpecialEventDialog(eventType = Spring) {}
}

@GrodnoRoadsPreview
@Composable
private fun PreviewSpecialEventDialogAutumn() = GrodnoRoadsM3ThemePreview {
    SpecialEventDialog(eventType = Autumn) {}
}