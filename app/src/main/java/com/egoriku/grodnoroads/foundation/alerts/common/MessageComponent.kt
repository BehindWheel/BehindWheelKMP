package com.egoriku.grodnoroads.foundation.alerts.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.screen.map.domain.Source
import com.egoriku.grodnoroads.screen.map.domain.MessageItem

@Composable
fun MessageComponent(messages: List<MessageItem>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        messages.forEach {
            MessageRow(messageItem = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMessageComponent() {
    MessageComponent(
        messages = listOf(
            MessageItem(message = "Test message", source = Source.App),
            MessageItem(message = "Test message", source = Source.Viber),
            MessageItem(message = "Test message", source = Source.Telegram)
        )
    )
}