package com.egoriku.grodnoroads.compose.snackbar

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.Simple
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.WithAction
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarState
import com.egoriku.grodnoroads.compose.snackbar.ui.core.DismissableRow
import com.egoriku.grodnoroads.compose.snackbar.ui.internal.MessageWithActionItem
import com.egoriku.grodnoroads.compose.snackbar.ui.internal.SimpleMessageItem
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import kotlinx.coroutines.launch

@Composable
fun Snackbar(snackbarData: SnackbarData) {
    DismissableRow(onDismiss = snackbarData::dismiss) {
        when (val message = snackbarData.message) {
            is WithAction -> {
                MessageWithActionItem(
                    message = message,
                    onAction = snackbarData::dismiss
                )
            }
            is Simple -> {
                SimpleMessageItem(message)
            }
        }
    }
}

@Preview
@Composable
private fun SnakbarPreview() = GrodnoRoadsM3ThemePreview {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarState() }

    Box(modifier = Modifier.fillMaxSize()) {
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
                    scope.launch {
                        snackbarState.show(
                            message = WithAction(
                                title = "Службы определения геолокации выключены. Вы можете включить их в разделе настройки",
                                description = "Используются для доступа к данным карт и работы функций навигации",
                                onAction = {
                                    Toast.makeText(
                                        /* context = */ context,
                                        /* text = */ "action performed",
                                        /* duration = */Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        )
                    }
                }
            ) {
                Text(text = "with action")
            }
            Button(
                onClick = {
                    scope.launch {
                        snackbarState.show(
                            message = Simple(
                                title = "Службы определения геолокации выключены.",
                                description = "Используются для доступа к данным карт и работы функций навигации"
                            )
                        )
                    }
                }
            ) {
                Text(text = "only message")
            }
        }
    }
}