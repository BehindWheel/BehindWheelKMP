package com.egoriku.grodnoroads.map.mode.drive.alerts.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.map.domain.model.MessageItem
import com.egoriku.grodnoroads.map.domain.model.Source

@Composable
fun MessageRow(messageItem: MessageItem) {
    Row(modifier = Modifier.fillMaxWidth()) {
        SourceImage(
            modifier = Modifier.padding(end = 4.dp),
            source = messageItem.source
        )
        Text(
            text = messageItem.message,
            style = MaterialTheme.typography.body1
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewMessageRow() = GrodnoRoadsTheme {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        MessageRow(messageItem = MessageItem(message = "Test message", source = Source.App))
        MessageRow(messageItem = MessageItem(message = "Test message", source = Source.Viber))
        MessageRow(messageItem = MessageItem(message = "Test message", source = Source.Telegram))
    }
}