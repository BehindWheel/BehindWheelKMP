package com.egoriku.grodnoroads.screen.root.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.egoriku.grodnoroads.foundation.dialog.DialogContent
import com.egoriku.grodnoroads.foundation.dialog.content.DialogButton
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType.Autumn
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType.Spring

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadLampDialog(headlampType: HeadLampType, onClose: () -> Unit) {
    val dialogContent = when (headlampType) {
        Spring -> stringResource(id = R.string.headlamp_body_spring)
        Autumn -> stringResource(id = R.string.headlamp_body_autumn)
        else -> error("$headlampType not supported")
    }

    BasicAlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = false
        ),
        onDismissRequest = onClose
    ) {
        DialogContent {
            Column(verticalArrangement = Arrangement.Center) {
                Text(modifier = Modifier.padding(16.dp), text = dialogContent)
                HorizontalDivider()
                DialogButton(
                    modifier = Modifier.fillMaxWidth(),
                    textResId = R.string.ok,
                    onClick = onClose
                )
            }
        }
    }
}


@com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
@Composable
private fun PreviewHeadLampDialogSpring() =
    com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview {
        HeadLampDialog(headlampType = Spring) {}
    }

@com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
@Composable
private fun PreviewHeadLampDialogAutumn() =
    com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview {
        HeadLampDialog(headlampType = Autumn) {}
    }