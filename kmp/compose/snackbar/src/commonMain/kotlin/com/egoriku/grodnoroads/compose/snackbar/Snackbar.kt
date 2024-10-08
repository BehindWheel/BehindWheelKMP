package com.egoriku.grodnoroads.compose.snackbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Raw
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.ActionMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.SimpleMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarState
import com.egoriku.grodnoroads.compose.snackbar.tool.rememberToastHelper
import com.egoriku.grodnoroads.compose.snackbar.ui.core.DismissableRow
import com.egoriku.grodnoroads.compose.snackbar.ui.internal.MessageWithActionItem
import com.egoriku.grodnoroads.compose.snackbar.ui.internal.SimpleMessageItem
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import kotlinx.coroutines.launch

@Composable
fun Snackbar(snackbarData: SnackbarData) {
    DismissableRow(onDismiss = snackbarData::dismiss) {
        when (val message = snackbarData.message) {
            is ActionMessage -> {
                MessageWithActionItem(
                    message = message,
                    onAction = snackbarData::dismiss
                )
            }
            is SimpleMessage -> {
                SimpleMessageItem(message)
            }
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun SnakbarPreview() = GrodnoRoadsM3ThemePreview {
    val toastHelper = rememberToastHelper()
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarState() }

    fun showSnack(message: SnackbarMessage) {
        scope.launch {
            snackbarState.show(message)
        }
    }

    val isInPreview = LocalInspectionMode.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        SnackbarHost(
            hostState = snackbarState,
            modifier = Modifier.align(Alignment.BottomCenter),
            paddingValues = PaddingValues(16.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    showSnack(
                        message = ActionMessage(
                            title = Raw("Службы определения геолокации выключены. Вы можете включить их в разделе настройки"),
                            description = Raw("Используются для доступа к данным карт и работы функций навигации"),
                            onAction = {
                                if (!isInPreview) {
                                    toastHelper.show("action performed")
                                }
                            }
                        )
                    )
                },
                content = {
                    Text(text = "with action")
                }
            )
            Button(
                onClick = {
                    showSnack(
                        message = SimpleMessage(
                            title = Raw("Службы определения геолокации выключены."),
                            description = Raw("Используются для доступа к данным карт и работы функций навигации")
                        )
                    )
                },
                content = {
                    Text(text = "only message")
                }
            )
        }
    }
}
