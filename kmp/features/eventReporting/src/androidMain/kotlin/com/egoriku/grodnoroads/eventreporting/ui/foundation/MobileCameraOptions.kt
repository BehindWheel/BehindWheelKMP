package com.egoriku.grodnoroads.eventreporting.ui.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.AutoScrollLazyRow
import com.egoriku.grodnoroads.foundation.core.CenterVerticallyRow
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.FilterChip
import com.egoriku.grodnoroads.foundation.uikit.OutlinedTextField
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun MobileCameraOptions(onReportParamsChange: (ReportParams) -> Unit) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val inputErrorText = stringResource(R.string.reporting_mobile_camera_input_error)

    val speedLimits = remember { persistentListOf(40, 50, 60, 70, 80, 90) }
    var selectedSpeedLimit by rememberMutableState { speedLimits.first() }
    var inputText by rememberMutableState { "" }

    var isValidateInput by rememberMutableState { false }
    val errorLabel by rememberMutableState(inputText, isValidateInput) {
        if (isValidateInput && inputText.isBlank()) inputErrorText
        else null
    }

    LaunchedEffect(selectedSpeedLimit, inputText) {
        onReportParamsChange(
            ReportParams.MobileCameraReport(
                speedLimit = selectedSpeedLimit,
                cameraInfo = inputText
            )
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CenterVerticallyRow(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.reporting_mobile_camera_header),
                style = MaterialTheme.typography.titleMedium
            )
        }

        val indexToScroll by remember {
            derivedStateOf { speedLimits.indexOfFirst { it == selectedSpeedLimit } }
        }
        AutoScrollLazyRow(indexToScroll = indexToScroll) {
            items(speedLimits) { limit ->
                val selected = selectedSpeedLimit == limit

                FilterChip(
                    label = {
                        Text(
                            text = stringResource(R.string.reporting_mobile_camera_speed, limit),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    selected = selected,
                    onClick = {
                        selectedSpeedLimit = limit
                        focusManager.clearFocus()
                    }
                )
            }
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
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
            label = stringResource(R.string.reporting_mobile_camera_input_hint),
            supportingText = errorLabel,
            onFocusChange = { isValidateInput = true }
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun MobileCameraOptionsPreview() = GrodnoRoadsM3ThemePreview {
    MobileCameraOptions(onReportParamsChange = {})
}