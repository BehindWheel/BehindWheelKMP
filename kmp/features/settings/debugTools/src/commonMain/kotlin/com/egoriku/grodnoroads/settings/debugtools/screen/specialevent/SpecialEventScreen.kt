package com.egoriku.grodnoroads.settings.debugtools.screen.specialevent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size
import com.egoriku.grodnoroads.specialevent.domain.model.EventType
import com.egoriku.grodnoroads.specialevent.screen.SpecialEventDialog

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SpecialEventScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var specialEventType by rememberMutableState<EventType?> { null }

    Scaffold(
        modifier = modifier,
        topBar = {
            SettingsTopBar(
                title = "Special Events",
                onBack = onBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrimaryInverseCircleButton(
                    onClick = { specialEventType = EventType.Spring },
                    size = Size.Small
                ) {
                    Text(text = "🌸")
                }
                PrimaryInverseCircleButton(
                    onClick = { specialEventType = EventType.Autumn },
                    size = Size.Small
                ) {
                    Text(text = "🍁")
                }
            }
        }

        specialEventType?.let { eventType ->
            SpecialEventDialog(
                eventType = eventType,
                onClose = { _ -> specialEventType = null }
            )
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun SpecialEventScreenPreview() = GrodnoRoadsM3ThemePreview {
    SpecialEventScreen(onBack = {})
}
