package com.egoriku.grodnoroads.screen.root.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.button.DialogButton
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType.Autumn
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType.Spring
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HeadLampDialog(headlampType: HeadLampType, onClose: () -> Unit) {
    val dialogContent = when (headlampType) {
        Spring -> stringResource(id = R.string.headlamp_body_spring)
        Autumn -> stringResource(id = R.string.headlamp_body_autumn)
        else -> error("$headlampType not supported")
    }

    AlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = false
        ),
        onDismissRequest = onClose,
        text = {
            Text(text = dialogContent)
        },
        buttons = {
            Column(verticalArrangement = Arrangement.Center) {
                Divider()
                DialogButton(
                    modifier = Modifier.fillMaxWidth(),
                    textResId = R.string.ok,
                    onClick = onClose
                )
            }
        }
    )
}


@Preview(locale = "ru")
@Composable
private fun PreviewHeadLampDialogSpring() {
    GrodnoRoadsTheme {
        Surface {
            HeadLampDialog(headlampType = Spring) {}
        }
    }
}

@Preview(locale = "ru")
@Composable
private fun PreviewHeadLampDialogAutumn() {
    GrodnoRoadsTheme {
        Surface {
            HeadLampDialog(headlampType = Autumn) {}
        }
    }
}