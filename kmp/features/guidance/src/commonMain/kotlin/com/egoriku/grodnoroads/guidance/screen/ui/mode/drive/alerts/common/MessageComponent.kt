package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.guidance.domain.model.MessageItem
import com.egoriku.grodnoroads.shared.models.MessageSource.App
import com.egoriku.grodnoroads.shared.models.MessageSource.Telegram
import com.egoriku.grodnoroads.shared.models.MessageSource.Viber
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MessageComponent(
    messages: ImmutableList<MessageItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        messages.forEach {
            MessageRow(messageItem = it)
        }
    }
}

@PreviewGrodnoRoads
@Composable
private fun PreviewMessageComponentPreview() = GrodnoRoadsM3ThemePreview {
    MessageComponent(
        messages = persistentListOf(
            MessageItem(message = "Test message 1\nTest message 1", messageSource = App),
            MessageItem(message = "Test message 2", messageSource = Viber),
            MessageItem(message = "Test message 3", messageSource = Telegram)
        )
    )
}
