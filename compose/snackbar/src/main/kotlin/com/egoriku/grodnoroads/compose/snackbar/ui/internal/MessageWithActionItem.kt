package com.egoriku.grodnoroads.compose.snackbar.ui.internal

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Raw
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Resource
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.ActionMessage
import com.egoriku.grodnoroads.compose.snackbar.ui.core.SnackbarSurface
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryInverseCircleButton
import com.egoriku.grodnoroads.foundation.uikit.button.common.Size.Small

@Composable
fun MessageWithActionItem(message: ActionMessage, onAction: () -> Unit) {
    SnackbarSurface {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = when (val title = message.title) {
                        is Raw -> title.text
                        is Resource -> stringResource(id = title.id)
                    },
                )
                PrimaryInverseCircleButton(
                    size = Small,
                    onClick = {
                        onAction()
                        message.onAction()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
            val description = message.description
            if (description != null) {
                DisabledText(
                    modifier = Modifier.fillMaxWidth(),
                    text = when (description) {
                        is Raw -> description.text
                        is Resource -> stringResource(id = description.id)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun MessageWithActionItemPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        MessageWithActionItem(
            message = ActionMessage(
                title = Raw("Доступ к геолокации запрещен."),
                description = Raw("Используется для доступа к данным карт"),
                onAction = {}
            ),
            onAction = {}
        )
        MessageWithActionItem(
            message = ActionMessage(
                title = Raw("Доступ к геолокации запрещен."),
                onAction = {}
            ),
            onAction = {}
        )
    }
}