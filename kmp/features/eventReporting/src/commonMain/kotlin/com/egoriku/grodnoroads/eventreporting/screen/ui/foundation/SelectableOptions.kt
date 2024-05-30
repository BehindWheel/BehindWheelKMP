package com.egoriku.grodnoroads.eventreporting.screen.ui.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.dialog_input_hint
import com.egoriku.grodnoroads.eventreporting.domain.Reporting
import com.egoriku.grodnoroads.eventreporting.screen.ui.util.toStringResource
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.OutlinedTextField
import com.egoriku.grodnoroads.foundation.uikit.RadioButton
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SelectableOptions(
    reportType: Reporting.ReportType,
    onReportParamsChange: (ReportParams) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    var selectedOption by rememberMutableState(reportType) {
        reportType.items.first()
    }
    var inputText by rememberMutableState { "" }

    LaunchedEffect(reportType, selectedOption, inputText) {
        onReportParamsChange(
            ReportParams.EventReport(
                mapEventType = selectedOption.mapEventType,
                shortMessage = selectedOption.toSend,
                message = inputText.ifEmpty { selectedOption.toSend }
            )
        )
    }

    reportType.items.forEach { entry ->
        RadioButtonListItem(
            text = stringResource(entry.toStringResource()),
            selected = selectedOption == entry,
            onSelect = {
                selectedOption = entry
                focusManager.clearFocus()
            }
        )
    }
    VerticalSpacer(16.dp)
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
        onValueChange = { inputText = it },
        label = stringResource(Res.string.dialog_input_hint)
    )
}

@Composable
private fun RadioButtonListItem(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = selected,
                role = Role.RadioButton,
                onValueChange = { onSelect() }
            )
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f),
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun SelectableOptionsPreview() = GrodnoRoadsM3ThemePreview {
    Column {
        SelectableOptions(
            reportType = Reporting.ReportType.RoadIncidents,
            onReportParamsChange = {}
        )
    }
}