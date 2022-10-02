package com.egoriku.grodnoroads.foundation.alerts.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.map.domain.model.Source
import com.egoriku.grodnoroads.map.domain.model.MessageItem

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

@Preview(showBackground = true)
@Composable
private fun PreviewMessageRow() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        MessageRow(messageItem = MessageItem(message = "Test message", source = Source.App))
        MessageRow(messageItem = MessageItem(message = "Test message", source = Source.Viber))
        MessageRow(messageItem = MessageItem(message = "Test message", source = Source.Telegram))
    }
}