package com.egoriku.grodnoroads.eventreporting.screen.ui.foundation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.dialog_input_hint
import com.egoriku.grodnoroads.compose.resources.reporting_input_length_error
import com.egoriku.grodnoroads.compose.resources.reporting_mobile_camera_input_hint
import com.egoriku.grodnoroads.eventreporting.domain.Reporting.ReportType
import com.egoriku.grodnoroads.eventreporting.screen.MAX_CAMERA_DESCRIPTION_SYMBOLS
import com.egoriku.grodnoroads.eventreporting.screen.MAX_EVENT_DESCRIPTION_SYMBOLS
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.uikit.OutlinedTextField
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ReportingOptionalMessage(
    reportType: ReportType,
    onUpdateMessage: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var inputText by rememberMutableState(reportType) { "" }

    LaunchedEffect(inputText) {
        inputText.trim().run(onUpdateMessage)
    }

    when (reportType) {
        ReportType.MobileCamera -> {
            val inputErrorText = stringResource(
                Res.string.reporting_input_length_error,
                MAX_CAMERA_DESCRIPTION_SYMBOLS
            )
            val errorLabel by rememberMutableState(inputText) {
                when {
                    inputText.length > MAX_CAMERA_DESCRIPTION_SYMBOLS -> inputErrorText
                    else -> null
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                value = inputText,
                isError = errorLabel != null,
                onValueChange = { inputText = it },
                supportingText = errorLabel,
                label = stringResource(Res.string.reporting_mobile_camera_input_hint)
            )
        }
        ReportType.Other, ReportType.RoadIncidents, ReportType.TrafficPolice -> {
            val inputErrorText = stringResource(
                Res.string.reporting_input_length_error,
                MAX_EVENT_DESCRIPTION_SYMBOLS
            )
            val errorLabel by rememberMutableState(inputText) {
                when {
                    inputText.length > MAX_EVENT_DESCRIPTION_SYMBOLS -> inputErrorText
                    else -> null
                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                value = inputText,
                isError = errorLabel != null,
                onValueChange = { inputText = it },
                supportingText = errorLabel,
                label = stringResource(Res.string.dialog_input_hint)
            )
        }
    }
}
