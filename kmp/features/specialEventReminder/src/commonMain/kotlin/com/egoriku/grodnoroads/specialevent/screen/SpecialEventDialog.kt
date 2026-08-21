package com.egoriku.grodnoroads.specialevent.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.event_reminder_autumn_body
import com.egoriku.grodnoroads.compose.resources.event_reminder_dont_show_today
import com.egoriku.grodnoroads.compose.resources.event_reminder_spring_body
import com.egoriku.grodnoroads.compose.resources.ok
import com.egoriku.grodnoroads.foundation.common.ui.dialog.DialogContent
import com.egoriku.grodnoroads.foundation.common.ui.dialog.content.DialogButton
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.listitem.CheckBoxListItem
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.domain.model.EventType.Autumn
import com.egoriku.grodnoroads.specialevent.domain.model.EventType.Spring
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialEventDialog(
    eventType: EventType,
    onClose: (dismissToday: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicAlertDialog(
        modifier = modifier,
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = false
        ),
        onDismissRequest = { onClose(false) }
    ) {
        SpecialEventDialogContent(
            eventType = eventType,
            onClose = onClose
        )
    }
}

@Composable
private fun SpecialEventDialogContent(
    eventType: EventType,
    onClose: (dismissToday: Boolean) -> Unit
) {
    var dismissToday by rememberMutableState { false }

    val emoji = when (eventType) {
        Spring -> "🌸🎒"
        Autumn -> "🍁🎒"
    }

    val dialogContent = when (eventType) {
        Spring -> stringResource(Res.string.event_reminder_spring_body)
        Autumn -> stringResource(Res.string.event_reminder_autumn_body)
    }

    DialogContent {
        VerticalSpacer(16.dp)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = dialogContent,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            CheckBoxListItem(
                text = stringResource(Res.string.event_reminder_dont_show_today),
                isChecked = dismissToday,
                onCheckedChange = { dismissToday = it }
            )
        }
        VerticalSpacer(8.dp)
        HorizontalDivider()
        DialogButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.ok),
            onClick = { onClose(dismissToday) }
        )
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun SpecialEventDialogSpringPreview() = GrodnoRoadsM3ThemePreview {
    SpecialEventDialogContent(eventType = Spring, onClose = {})
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun SpecialEventDialogAutumnPreview() = GrodnoRoadsM3ThemePreview {
    SpecialEventDialogContent(eventType = Autumn, onClose = {})
}
