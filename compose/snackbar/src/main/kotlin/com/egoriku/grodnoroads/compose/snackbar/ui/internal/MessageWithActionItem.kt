package com.egoriku.grodnoroads.compose.snackbar.ui.internal

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.WithAction
import com.egoriku.grodnoroads.compose.snackbar.ui.core.SnackbarSurface
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.DisabledText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MessageWithActionItem(message: WithAction, onAction: () -> Unit) {
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
                    text = message.title,
                )
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    IconButton(
                        onClick = {
                            onAction()
                            message.onAction()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            tint = MaterialTheme.colorScheme.tertiary,
                            contentDescription = null
                        )
                    }
                }
            }
            if (!message.description.isNullOrEmpty()) {
                DisabledText(
                    modifier = Modifier.fillMaxWidth(),
                    text = message.description,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun MessageWithActionItemPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(16.dp)) {
        MessageWithActionItem(
            message = WithAction(
                title = "Доступ к геолокации запрещен.",
                description = "Используются для доступа к данным карт",
                onAction = {}
            ),
            onAction = {}
        )
    }
}